package org.actionfeed.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 */
public class InviteFormModel extends BaseFormModel implements Serializable {

    private static final long serialVersionUID = -4030621663121556024L;

    private ArrayList<String> ids = new ArrayList<String>();

    private String excludeIds = "";

    public InviteFormModel() {
    }

    public String[] getIds() {
        return (String[]) ids.toArray(new String[]{});
    }

    public void setIds(String[] ids) {
        this.ids.addAll(Arrays.asList(ids));
    }

    public String getId(int index) {
        if (ids.size() < index) {
            return ids.get(index);
        }
        return "";
    }

    public void setId(int index, final String value) {
        ids.add(index, value);
    }

    public String getExcludeIds() {
        return excludeIds;
    }

    public void setExcludeIds(String excludeIds) {
        this.excludeIds = excludeIds;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("InviteFormModel");
        sb.append("{ids=").append(ids);
        sb.append("{excludeIds=").append(excludeIds);
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
