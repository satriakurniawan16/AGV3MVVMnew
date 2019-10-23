package com.satria.authenticguards.agv3mvvm.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.satria.authenticguards.R;
import com.satria.authenticguards.agv3mvvm.Adapter.BrandAdapter;
import com.satria.authenticguards.agv3mvvm.Adapter.PromoAdapter;
import com.satria.authenticguards.agv3mvvm.View.DetailNotifActivity;
import com.satria.authenticguards.agv3mvvm.View.EditProfileActivity;
import com.satria.authenticguards.agv3mvvm.model.Brand;
import com.satria.authenticguards.agv3mvvm.model.Promo;
import com.satria.authenticguards.agv3mvvm.utils.DataRequest;
import com.satria.authenticguards.agv3mvvm.utils.JsonUtil;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View rootView;
    Button mButtonAunthenticStore,
            mButtonMoreInfoStories,
            mButtonGoProfile,
            mButtonHighlight;

    TextView mButtonSeeAllStories,
             mButtonSeeAllPromo,
             mButtonSeeAllBrand;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser firebaseUser;

    String token, finalImage;

    List<String> imageUrls = new ArrayList<String>();

    CardView completeProfileCard;
    private BrandAdapter mAdapterBrand;
    private PromoAdapter mAdapterPromo;
    RecyclerView recyclerViewBrand, recylerPromo;
    private ArrayList<String> mDataId,
            mDataIdPromo;
    JsonUtil jsonUtil=new JsonUtil();

    private ArrayList<Brand> mData = new ArrayList<>();
    private ArrayList<Promo> mDataPromo = new ArrayList<>();

    ProgressBar mProgressBarPromo,
            mProgressBarBrand;

    ShimmerFrameLayout mShimmerViewContainer;

    CarouselView carouselView;
    private ActionMode mActionMode;
    private ActionMode mActionPromo;

    public HomeFragment() {
        // Required empty public constructor
    }

    ViewListener viewListener=new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            imageUrls=jsonUtil.imgUrls;
            View customView=getActivity().getLayoutInflater().inflate(R.layout.home_viewcustom,null);
            ImageView imageView=customView.findViewById(R.id.myImage_home);
            Picasso.get().load(imageUrls.get(position)).into(imageView);
            return customView;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        setUpView(rootView);
        generateView(rootView);
        return rootView;
    }
    private void completeProfileLayout(){
        final DatabaseReference dbf=FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());
        dbf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("completeProfile").exists()) {
                    String complete = dataSnapshot.child("completeProfile").getValue().toString();
                    if (complete.equals("true")) {
                        completeProfileCard.setVisibility(View.GONE);
                    } else {
                        completeProfileCard.setVisibility(View.VISIBLE);
                    }
                } else {
                    completeProfileCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
        jsonUtil.getBrandHome(getContext(),mAdapterBrand,mProgressBarBrand);
        jsonUtil.getPromoHome(getContext(),token,mAdapterPromo,mProgressBarPromo);
        jsonUtil.getDataSliderHome(getContext(),viewListener,carouselView,mShimmerViewContainer);

    }

    private void generateView(View rootView) {

        if(firebaseUser != null ) {

            firebaseUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            token = task.getResult().getToken();
                            Log.d("homefragment", "onCompleteBaru: " + token);
                            String result = "";
                            DataRequest.setUser(getApplicationContext(), token);
                        }
                    });
            completeProfileLayout();
        }

        goAllBrand();
        goAllpromo();
        goAutenticStore();
        goHighlight();
        goInfoStories();
        goProfile();
        adapterBrand();
        adapterPromo();
    }

    private void setUpView(View rootView) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();

        completeProfileCard = rootView.findViewById(R.id.cardProfile);

        mProgressBarPromo = (ProgressBar) rootView.findViewById(R.id.progress_promo);
        mProgressBarBrand = (ProgressBar) rootView.findViewById(R.id.progressBrand);

        mShimmerViewContainer = (ShimmerFrameLayout) rootView.findViewById(R.id.shimer_view_container);
        carouselView = rootView.findViewById(R.id.slider);

        mButtonGoProfile = rootView.findViewById(R.id.goProfile);
        mButtonHighlight = (Button) rootView.findViewById(R.id.more_info_highlight);
        mButtonSeeAllPromo = (TextView) rootView.findViewById(R.id.seeAllPromo);
        mButtonMoreInfoStories = rootView.findViewById(R.id.more_info_ag_stories);
        mButtonAunthenticStore = rootView.findViewById(R.id.more_info_authentic_store);
        mButtonSeeAllBrand = rootView.findViewById(R.id.seeAllBrand);

        recylerPromo = (RecyclerView) rootView.findViewById(R.id.list_promo);
        recylerPromo.setHasFixedSize(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);

        recylerPromo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItemVisible = linearLayoutManager.findFirstVisibleItemPosition();
                if (firstItemVisible != 0 && firstItemVisible % jsonUtil.promos.size() == 0) {
                    recyclerView.getLayoutManager().scrollToPosition(0);
                }
            }
        });
        recylerPromo.setLayoutManager(linearLayoutManager);

        recylerPromo.setBackgroundColor(Color.parseColor("#ffffff"));
        PagerSnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(recylerPromo);

        recyclerViewBrand = (RecyclerView) rootView.findViewById(R.id.list_brand);
        recyclerViewBrand.setHasFixedSize(false);

    }

    private void goProfile(){
        //MUST CHANGE THE TARGET INTENT SOON

        mButtonGoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EditProfileActivity.class));
            }
        });
    }

    private void goHighlight(){
        mButtonHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DetailNotifActivity.class);
                intent.putExtra("hasil","0");
                startActivity(intent);
            }
        });
    }
    private void goAllpromo(){
        mButtonSeeAllPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailNotifActivity.class);
                startActivity(intent);
            }
        });
    }
    private void goInfoStories(){
        mButtonMoreInfoStories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailNotifActivity.class);
                intent.putExtra("hasil", "1");
                startActivity(intent);
            }
        });
    }
    private void goAutenticStore(){
        mButtonAunthenticStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailNotifActivity.class);
                startActivity(intent);
            }
        });
    }
    private void goAllBrand(){
        mButtonSeeAllBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailNotifActivity.class);
                startActivity(intent);
            }
        });
    }

    private void adapterBrand() {
        mAdapterBrand =new BrandAdapter(new BrandAdapter.ClickHandler() {
            @Override
            public void onItemClick(int position) {
                if (mActionMode != null) {
                    mAdapterBrand.toggleSelection(mDataId.get(position));
                    if (mAdapterBrand.selectionCount() == 0)
                        mActionMode.finish();
                    else
                        mActionMode.invalidate();
                    return;
                }
                Brand brand = jsonUtil.brands.get(position);
                Intent intent = new Intent(getApplicationContext(), DetailNotifActivity.class);
                intent.putExtra("id", brand.getId());
                intent.putExtra("name", brand.getName());
                intent.putExtra("image", brand.getImage());
                startActivity(intent);
            }
        },getApplicationContext(),jsonUtil.brands,mDataId );

        recyclerViewBrand.setAdapter(mAdapterBrand);
       }

       private void adapterPromo(){
        mAdapterPromo=new PromoAdapter(getApplicationContext(),jsonUtil.promos,mDataId);
        recylerPromo.setAdapter(mAdapterPromo);
       }

    }


