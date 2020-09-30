import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import model.view.AppModuleImpl;


import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

public class ManageBean {
//    private RichTable reFreshTable;
    private RichDialog handleDialogue;
    private RichTable refreshTable1;
    private RichTable refreshTable;

    public ManageBean() {
    }
    
    

    public AppModuleImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("AppModuleDataControl"); // Name of application module in datacontrolBinding.cpx
       AppModuleImpl appM = (AppModuleImpl)dc.getDataProvider();
        return appM;
    }
    AppModuleImpl appM = (AppModuleImpl)this.getAppModuleImpl();
    public void callSave(ActionEvent actionEvent) {
        try {
            appM.getDBTransaction().commit();
            System.out.println("Commit sucessfully......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void OnCreate(PopupFetchEvent popupFetchEvent) {
      BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
      oracle.binding.OperationBinding bp=bc.getOperationBinding("CreateInsert");
        System.out.println("POPOP.....");
      bp.execute();
        
    } 
    public void ondialogOk(DialogEvent dialogEvent) {


        if (DialogEvent.Outcome.ok == dialogEvent.getOutcome().ok) {
                BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
                oracle.binding.OperationBinding bp=bc.getOperationBinding("Commit1");
                bp.execute();
                System.out.println("DIALOGUE OK");
            AdfFacesContext.getCurrentInstance().addPartialTarget(refreshTable1);
            
            
            FacesContext context = FacesContext.getCurrentInstance();
                String currentView = context.getViewRoot().getViewId();
                ViewHandler vh = context.getApplication().getViewHandler();
                UIViewRoot x = vh.createView(context, currentView);
                x.setViewId(currentView);
                context.setViewRoot(x);
        } else  {

                BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
                oracle.binding.OperationBinding bp=bc.getOperationBinding("Rollback");
                bp.execute();
                  AdfFacesContext.getCurrentInstance().addPartialTarget(refreshTable1);

              }

    }

//    public void popup1FetchListener(PopupFetchEvent popupFetchEvent) {
//        // Add event code here...
//        ViewObject vo =  appM.getXxOmWashCostSubProcessT_VO1();
////        Row rw;
////        
////        //read comma separated file line by line
////     
////                    rw = vo.createRow();
////                    vo.setCurrentRow(rw);
////                    rw.setNewRowState(Row.STATUS_INITIALIZED);
////                    vo.insertRow(rw);
//
//
//
//
//
//    }

    public void setHandleDialogue(RichDialog handleDialogue) {
        this.handleDialogue = handleDialogue;
    }

    public RichDialog getHandleDialogue() {
        return handleDialogue;
    }

    public void setRefreshTable1(RichTable refreshTable1) {
        this.refreshTable1 = refreshTable1;
    }

    public RichTable getRefreshTable1() {
        return refreshTable1;
    }

    public void addPop(ActionEvent actionEvent) {
        BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        oracle.binding.OperationBinding bp=bc.getOperationBinding("CreateInsert");
          System.out.println("POPOP.....");
        bp.execute();
    }
    
    
    
    
}
