package maxcrowdfund.com.mvvm.ui.home.homeDetail.model;

public class CollateralModelP {
    String mCollateralSubHeading;
    String mCollateralTitle1;
    String mCollateralValue1;

    public CollateralModelP(String mCollateralSubHeading) {
        this.mCollateralSubHeading = mCollateralSubHeading;
    }

    public CollateralModelP(String mCollateralTitle1, String mCollateralValue1) {
        this.mCollateralTitle1 = mCollateralTitle1;
        this.mCollateralValue1 = mCollateralValue1;
    }

    public String getmCollateralSubHeading() {
        return mCollateralSubHeading;
    }

    public String getmCollateralTitle1() {
        return mCollateralTitle1;
    }

    public String getmCollateralValue1() {
        return mCollateralValue1;
    }

}
