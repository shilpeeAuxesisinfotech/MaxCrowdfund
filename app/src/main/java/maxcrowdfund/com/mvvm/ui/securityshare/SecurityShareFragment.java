package maxcrowdfund.com.mvvm.ui.securityshare;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import maxcrowdfund.com.R;
import maxcrowdfund.com.constant.Utils;
import maxcrowdfund.com.databinding.FragmentSecurityShareBinding;

public class SecurityShareFragment extends Fragment {
    FragmentSecurityShareBinding binding;
    private Activity mActivity;
    private boolean isIntroduction = false;
    private boolean isInvestment = false;
    private boolean isDocumentation = false;
    private boolean isInvestors = false;
    private boolean isUpdated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecurityShareBinding.inflate(inflater);
        mActivity = getActivity();
        Log.d(">>>>>>>", "onCreateView: ");
        getUI();
        return binding.getRoot();
    }

    private void getUI() {
        binding.lLIntroContent.setVisibility(View.VISIBLE);
        binding.lLInvestmentContant.setVisibility(View.GONE);
        binding.lLDocumentationContent.setVisibility(View.GONE);
        binding.lLInvestorsContent.setVisibility(View.GONE);
        binding.lLUpdatedContent.setVerticalGravity(View.GONE);

        binding.rLIntroClick.setOnClickListener(v -> {
            if (binding.lLIntroContent.isShown()) {
                Utils.slide_up(mActivity, binding.lLIntroContent);
                isIntroduction = false;
                binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                binding.lLIntroContent.setVisibility(View.GONE);
            } else {
                isIntroduction = true;
                binding.lLIntroContent.setVisibility(View.VISIBLE);
                Utils.slide_down(mActivity, binding.lLIntroContent);
                binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_up);
            }
            if (isInvestment) {
                if (binding.lLInvestmentContant.isShown()) {
                    Utils.slide_up(mActivity, binding.lLInvestmentContant);
                    isInvestment = false;
                    binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLInvestmentContant.setVisibility(View.GONE);
                }
            }
            if (isDocumentation) {
                if (binding.lLDocumentationContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLDocumentationContent);
                    isDocumentation = false;
                    binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLDocumentationContent.setVisibility(View.GONE);
                }
            }
            if (isInvestors) {
                if (binding.lLInvestorsContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLInvestorsContent);
                    isInvestors = false;
                    binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLInvestorsContent.setVisibility(View.GONE);
                }
            }
            if (isUpdated) {
                if (binding.lLUpdatedContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLUpdatedContent);
                    isUpdated = false;
                    binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLUpdatedContent.setVisibility(View.GONE);
                }
            }
        });
        binding.rLInvestmentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lLInvestmentContant.isShown()) {
                    Utils.slide_up(mActivity, binding.lLInvestmentContant);
                    isInvestment = false;
                    binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLInvestmentContant.setVisibility(View.GONE);
                } else {
                    binding.lLInvestmentContant.setVisibility(View.VISIBLE);
                    isInvestment = true;
                    Utils.slide_down(mActivity, binding.lLInvestmentContant);
                    binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                if (isIntroduction) {
                    if (binding.lLIntroContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLIntroContent);
                        isIntroduction = false;
                        binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLIntroContent.setVisibility(View.GONE);
                    }
                }
                if (isDocumentation) {
                    if (binding.lLDocumentationContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLDocumentationContent);
                        isDocumentation = false;
                        binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLDocumentationContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestors) {
                    if (binding.lLInvestorsContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestorsContent);
                        isInvestors = false;
                        binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestorsContent.setVisibility(View.GONE);
                    }
                }
                if (isUpdated) {
                    if (binding.lLUpdatedContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLUpdatedContent);
                        isUpdated = false;
                        binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLUpdatedContent.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.rLDocumentationClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lLDocumentationContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLDocumentationContent);
                    isDocumentation = false;
                    binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLDocumentationContent.setVisibility(View.GONE);
                } else {
                    binding.lLDocumentationContent.setVisibility(View.VISIBLE);
                    isDocumentation = true;
                    Utils.slide_down(mActivity, binding.lLDocumentationContent);
                    binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                if (isIntroduction) {
                    if (binding.lLIntroContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLIntroContent);
                        isIntroduction = false;
                        binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLIntroContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestment) {
                    if (binding.lLInvestorsContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestorsContent);
                        isInvestment = false;
                        binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestorsContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestors) {
                    if (binding.lLInvestorsContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestorsContent);
                        isInvestors = false;
                        binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestorsContent.setVisibility(View.GONE);
                    }
                }
                if (isUpdated) {
                    if ( binding.lLUpdatedContent.isShown()) {
                        Utils.slide_up(mActivity,  binding.lLUpdatedContent);
                        isUpdated = false;
                        binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLUpdatedContent.setVisibility(View.GONE);
                    }
                }

            }
        });
        binding.rLInvestorsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lLInvestorsContent.isShown()) {
                    Utils.slide_up(mActivity,  binding.lLInvestorsContent);
                    isInvestors = false;
                    binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLInvestorsContent.setVisibility(View.GONE);
                } else {
                    binding.lLInvestorsContent.setVisibility(View.VISIBLE);
                    isInvestors = true;
                    Utils.slide_down(mActivity,  binding.lLInvestorsContent);
                    binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }

                if (isIntroduction) {
                    if (binding.lLIntroContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLIntroContent);
                        isIntroduction = false;
                        binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLIntroContent.setVisibility(View.GONE);
                    }
                }

                if (isDocumentation) {
                    if ( binding.lLDocumentationContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLDocumentationContent);
                        isDocumentation = false;
                        binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLDocumentationContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestment) {
                    if (binding.lLInvestmentContant.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestmentContant);
                        isInvestment = false;
                        binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestmentContant.setVisibility(View.GONE);
                    }
                }
                if (isInvestors) {
                    if (binding.lLInvestorsContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestorsContent);
                        isInvestors = false;
                        binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestorsContent.setVisibility(View.GONE);
                    }
                }
                if (isUpdated) {
                    if (binding.lLUpdatedContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLUpdatedContent);
                        isUpdated = false;
                        binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLUpdatedContent.setVisibility(View.GONE);
                    }
                }
            }
        });
        binding.rLCardUpdatedClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.lLUpdatedContent.isShown()) {
                    Utils.slide_up(mActivity, binding.lLUpdatedContent);
                    isUpdated = false;
                    binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    binding.lLUpdatedContent.setVisibility(View.GONE);
                } else {
                    binding.lLUpdatedContent.setVisibility(View.VISIBLE);
                    isUpdated = true;
                    Utils.slide_down(mActivity, binding.lLUpdatedContent);
                    binding.tvUpdatedArrow.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                if (isIntroduction) {
                    if (binding.lLIntroContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLIntroContent);
                        isIntroduction = false;
                        binding.tvIntroArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLIntroContent.setVisibility(View.GONE);
                    }
                }

                if (isDocumentation) {
                    if (binding.lLDocumentationContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLDocumentationContent);
                        isDocumentation = false;
                        binding.tvDocumentationArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLDocumentationContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestors) {
                    if (binding.lLInvestorsContent.isShown()) {
                        Utils.slide_up(mActivity, binding.lLInvestorsContent);
                        isInvestors = false;
                        binding.tvInvestorsArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestorsContent.setVisibility(View.GONE);
                    }
                }
                if (isInvestment) {
                    if ( binding.lLInvestmentContant.isShown()) {
                        Utils.slide_up(mActivity,  binding.lLInvestmentContant);
                        isInvestment = false;
                        binding.tvInvestmentArrow.setBackgroundResource(R.drawable.ic_arrow_down);
                        binding.lLInvestmentContant.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
