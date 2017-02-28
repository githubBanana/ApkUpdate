package com.xs.versionupdate.updateUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 *
 *  xs.lin
 *  2017/2/22 11:50
 */

public class UpdateDialog extends DialogFragment {

    private static OnClickUpdate mOnClickUpdate;

    public static UpdateDialog getDialog(OnClickUpdate onClickUpdate) {
        mOnClickUpdate = onClickUpdate;
        final UpdateDialog updateDialog = new UpdateDialog();
        Bundle bundle = new Bundle();
        updateDialog.setArguments(bundle);
        return updateDialog;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity()).setTitle("版本更新").setMessage("是否更新？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mOnClickUpdate != null)
                            mOnClickUpdate.sure();
                    }
                })
                .setNegativeButton("取消",null).show();

    }

    public interface OnClickUpdate{
        void sure();
    }
}
