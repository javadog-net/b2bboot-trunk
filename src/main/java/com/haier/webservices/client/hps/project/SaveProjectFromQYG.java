
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveProjectFromQYG", propOrder = {
    "arg0"
})
public class SaveProjectFromQYG {

    protected List<ProjectSaveParam> arg0;

     
    public List<ProjectSaveParam> getArg0() {
        if (arg0 == null) {
            arg0 = new ArrayList<ProjectSaveParam>();
        }
        return this.arg0;
    }

}
