package rs.projekat.enrg.energymeters.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by toshiba on 02/08/2016.
 */
public class ProgressDialogCustom extends ProgressDialog {
    public ProgressDialogCustom(Context context) {
        super(context);
    }

    public void showDialog(String msg){
        if (!isShowing()){
            // ako hocemo da ga pokazemo
            setMessage(msg);
            show();
        }
    }

    public void hideDialog(){
        if(isShowing()){
            dismiss();
        }
    }

}
