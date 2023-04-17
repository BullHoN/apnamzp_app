package com.avit.apnamzp.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.L;
import com.avit.apnamzp.MainActivity;
import com.avit.apnamzp.R;
import com.avit.apnamzp.databinding.FragmentProfileBinding;
import com.avit.apnamzp.dialogs.LoadingDialog;
import com.avit.apnamzp.localdb.SharedPrefNames;
import com.avit.apnamzp.localdb.User;
import com.avit.apnamzp.network.NetworkApi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;
    private String TAG = "profileFragmentTag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        sharedPreferences = getActivity().getSharedPreferences(SharedPrefNames.SHAREDDB_NAME, Context.MODE_PRIVATE);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        binding.referandearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createReferAndEarnLink();
            }
        });

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_userDetailsFragment);
            }
        });

        binding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBussinessDetailsFragment("About Us",R.string.about_us_text);
            }
        });

        binding.privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBussinessDetailsFragment("Privacy Policy",R.string.privacy_policy_text);
            }
        });

        binding.terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBussinessDetailsFragment("Terms And Condition",R.string.terms_and_condition_text);
            }
        });

        binding.shippingPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBussinessDetailsFragment("Shipping Policy",R.string.shipping_policy_text);
            }
        });

        binding.refundAndCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBussinessDetailsFragment("Refund And Cancellation",R.string.refund_and_cancellation_policy);
            }
        });

        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_contatUsFragment);
            }
        });

        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return binding.getRoot();
    }

    private void createReferAndEarnLink(){

        String invitationUrl = User.getReferAndEarnLink(getContext());
        if(invitationUrl != null && invitationUrl.length() != 0){
            shareLink(invitationUrl);
            return;
        }

        String userPhoneNo = User.getPhoneNumber(getContext());
        String link = NetworkApi.SERVER_URL + "/referral?invitedby=" + userPhoneNo;

        FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(link))
                .setDomainUriPrefix("https://app.apnamzp.in")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder("com.avit.apnamzp")
                                .build())
                .setSocialMetaTagParameters(
                        new DynamicLink.SocialMetaTagParameters.Builder()
                                .setTitle("Open Account at ApnaMZP Using Link")
                                .setDescription("Earn Points And Get Exciting Prizes")
                                .setImageUrl(Uri.parse("https://www.woodenstreet.com/mobile/images-m/referafriend/banner.jpg"))
                                .build())
                .buildShortDynamicLink()
                .addOnSuccessListener(new OnSuccessListener<ShortDynamicLink>() {
                    @Override
                    public void onSuccess(ShortDynamicLink shortDynamicLink) {
                        String mInvitationUrl = shortDynamicLink.getShortLink().toString();
                        User.setReferAndEarnLink(getContext(),mInvitationUrl);

                        shareLink(mInvitationUrl);
                    }
                });
    }

    private void shareLink(String link){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Refer And Earn \n " + link);
        startActivity(Intent.createChooser(shareIntent,"Choose One"));
    }

    private void openBussinessDetailsFragment(String title,int string_id){
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putInt("string_id",string_id);

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_profileFragment_to_bussinessDetailsFragment,bundle);
    }

}