package org.tianjyan.luban.client.internal;

import org.tianjyan.luban.aidl.OutPara;
import org.tianjyan.luban.client.OutParaManager;

import java.util.ArrayList;
import java.util.List;

public class OutParaManagerInternal {
    public OutParaManagerInternal()
    {
        temp = new ArrayList<OutPara>();
        userInterface = new OutParaManager();
    }

    private List<OutPara> temp;
    private OutParaManager userInterface;

    public OutParaManager getUserInterface()
    {
        return userInterface;
    }

    public void register(String ParaName, String alias, Object...extras){
        if(null == ParaName || null == alias){
            return;
        }
        if(alias.length() > 4){
            alias = alias.substring(0, 3) + ".";
        }

        OutPara Para = new OutPara();
        Para.setKey(ParaName);
        Para.setAlias(alias);
        Para.setRegistering(true);
        Para.setDisplayProperty(OutPara.DISPLAY_NORMAL);
        if (null != extras && extras.length > 0)
        {
            if (extras[0] instanceof Boolean)
            {
                boolean isGlobal = Boolean.TRUE.equals(extras[0]);
                Para.setGlobal(isGlobal);
            }
        }

        temp.add(Para);
    }

    public void defaultOutParasInAC(String... ParaNames){
        int len = ParaNames.length;
        for(int i = 0 ; i < temp.size() ; i++){
            if(OutPara.DISPLAY_DISABLE != temp.get(i).getDisplayProperty()){
                temp.get(i).setDisplayProperty(OutPara.DISPLAY_NORMAL);
            }
        }

        for(int i = 0 ; i < temp.size() ; i++){
            for(int j = 0 ; j < len ; j++){
                if(temp.get(i).getKey().equals(ParaNames[j])){
                    temp.get(i).setDisplayProperty(OutPara.DISPLAY_AC);
                }
            }
        }

        int[] pos = new int[temp.size()];
        for(int i = 0 ; i < len ; i++){
            for(int j = 0 ; j < temp.size() ; j++){
                if(ParaNames[i].equals(temp.get(j).getKey())){
                    pos[i] = j;
                }
            }
        }

        List<OutPara> s_temp = new ArrayList<OutPara>();
        for(int i = 0 ; i < len ;i++){
            s_temp.add(i, temp.get(pos[i]));
        }

        List<OutPara> t_temp = new ArrayList<OutPara>();
        t_temp.addAll(temp);
        List<OutPara> tl = new ArrayList<OutPara>();
        for(int i = 0 ; i < len ;i++){
            tl.add(t_temp.get(pos[i]));
        }
        for(int i = 0 ; i < len ;i++){
            t_temp.remove(tl.get(i));
        }

        temp.clear();
        temp.addAll(s_temp);
        temp.addAll(t_temp);

    }

    public void setOutParasDisable(){
        for(int i = 0 ; i < temp.size(); i++){
            temp.get(i).setDisplayProperty(OutPara.DISPLAY_DISABLE);
        }
    }

    public void setOutParasDisable(String... ParaNames){
        if(null != ParaNames){
            int len = ParaNames.length;

            for(int i = 0 ; i < temp.size(); i++){
                for(int j = 0 ; j < len ; j++){
                    if(temp.get(i).getKey().equals(ParaNames[j])){
                        temp.get(i).setDisplayProperty(OutPara.DISPLAY_DISABLE);
                    }
                }
            }
        }
    }

    public OutPara[] getAndClearTempParas()
    {
        OutPara[] result = temp.toArray(new OutPara[]{});
        temp.clear();
        return result;
    }
}