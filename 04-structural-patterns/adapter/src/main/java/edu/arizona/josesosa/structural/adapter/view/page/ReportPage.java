package edu.arizona.josesosa.structural.adapter.view.page;

import edu.arizona.josesosa.structural.adapter.container.Container;
import edu.arizona.josesosa.structural.adapter.view.ReportController;

public class ReportPage implements Page {

    /* (non-Javadoc)
     * @see view.Page#render()
     */
    @Override
    public String render() {
        System.out.println("---------Report page--------");
        System.out.println("\nReport:");
        System.out.println("\tTotal salary:" + ((ReportController) Container.context.get("reportController")).getReportPersonSalaryTotal());
        System.out.println("\tMarriage stat:" + ((ReportController) Container.context.get("reportController")).getReportPersonMarriedStat());
        System.out.println("\n---------           --------");
        return null;
    }
}
