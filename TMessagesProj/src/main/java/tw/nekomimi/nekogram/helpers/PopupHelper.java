package tw.nekomimi.nekogram.helpers;

import android.content.Context;
import android.view.View;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.BulletinFactory;

/**
 * LinQigram trimmed PopupHelper: only the profile ID popup
 * (copy ID / show datacenter). Nekogram's registration-date and
 * TL-viewer extras are intentionally not ported.
 */
public class PopupHelper {

    public static void showIdPopup(BaseFragment fragment, View anchorView, long id, int dc, long did, long userId, float x, float y) {
        Context context = fragment.getParentActivity();
        if (context == null) {
            return;
        }
        ActionBarPopupWindow.ActionBarPopupWindowLayout popupLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(context, R.drawable.popup_fixed_alert4, fragment.getResourceProvider(), ActionBarPopupWindow.ActionBarPopupWindowLayout.FLAG_USE_SWIPEBACK);
        popupLayout.setFitItems(true);
        ActionBarPopupWindow popupWindow = AlertsCreator.createSimplePopup(fragment, popupLayout, anchorView, x, y);
        if (id != 0) {
            ActionBarMenuItem.addItem(popupLayout, R.drawable.msg_copy, LocaleController.getString(R.string.CopyID), false, fragment.getResourceProvider()).setOnClickListener(v -> {
                popupWindow.dismiss();
                AndroidUtilities.addToClipboard(String.valueOf(id));
                BulletinFactory.of(fragment).createCopyBulletin(LocaleController.formatString(R.string.TextCopied)).show();
            });
        }
        if (dc != 0) {
            ActionBarMenuSubItem subItem = ActionBarMenuItem.addItem(popupLayout, R.drawable.msg_satellite, LocaleController.getString(R.string.DatacenterStatusShort), false, fragment.getResourceProvider());
            subItem.setSubtext("DC " + dc);
        }
        popupLayout.setParentWindow(popupWindow);
    }

    public static void showCopyPopup(BaseFragment fragment, CharSequence title, View anchorView, float x, float y, Runnable callback) {
        Context context = fragment.getParentActivity();
        if (context == null) {
            return;
        }
        ActionBarPopupWindow.ActionBarPopupWindowLayout popupLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(context, R.drawable.popup_fixed_alert4, fragment.getResourceProvider());
        popupLayout.setFitItems(true);
        ActionBarPopupWindow popupWindow = AlertsCreator.createSimplePopup(fragment, popupLayout, anchorView, x, y);
        ActionBarMenuItem.addItem(popupLayout, R.drawable.msg_copy, title, false, fragment.getResourceProvider()).setOnClickListener(v -> {
            popupWindow.dismiss();
            callback.run();
        });
        popupLayout.setParentWindow(popupWindow);
    }
}
