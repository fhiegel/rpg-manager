package com.dedale.business.ability.ihm.view;

import java.util.function.Supplier;

import com.dedale.business.ability.model.AbilityTemplate;
import com.dedale.frmwrk.action.DialogAction;
import com.dedale.frmwrk.action.DialogUpdateAction;
import com.dedale.frmwrk.view.controller.AbstractController;
import com.dedale.frmwrk.view.controller.ControllerException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Formulaire de mise à jour d'un élément d'arbre technologique
 * 
 * @back : {@link AbilityTemplateListView}
 */
public class AbilityTemplateUpdateForm extends AbstractController<AnchorPane> {
    
    @FXML
    private TextField nameLabel;
    @FXML
    private TextArea conditionsLabel;
    @FXML
    private TextArea descriptionLabel;
    
    private Stage dialogStage;
    private DialogUpdateAction<AbilityTemplate> validateUpdateAction = this::updateAbilityTemplate;
    private DialogAction validateAction;
    private DialogAction cancelAction = DialogAction.closeDialog(() -> dialogStage);
    
    @FXML
    private void validate() throws ControllerException {
        validateAction.execute();
    }
    
    @FXML
    private void cancel() throws ControllerException {
        cancelAction.execute();
    }
    
    private void showAbilityTemplate(AbilityTemplate abilityTemplate) {
        if (abilityTemplate != null) {
            nameLabel.setText(abilityTemplate.getName());
            conditionsLabel.setText(abilityTemplate.getConditions());
            descriptionLabel.setText(abilityTemplate.getDescription());
        } else {
            nameLabel.setText("");
            conditionsLabel.setText("");
            descriptionLabel.setText("");
        }
    }
    
    public void initDialog(Supplier<AbilityTemplate> updateItemProvider, DialogAction viewCallback) throws ControllerException {
        // Create the dialog Stage.
        if (dialogStage == null) {
            
            dialogStage = new Stage();
            dialogStage.setTitle("Dialog -> Edit Tech");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(getControllerManager().getRootController().getStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);
        }
        
        AbilityTemplate abilityTemplate = updateItemProvider.get();
        showAbilityTemplate(abilityTemplate);
        
        validateAction = validateUpdateAction.action(updateItemProvider).andThen(viewCallback).thenClose(dialogStage);
        dialogStage.showAndWait();
    }
    
    private void updateAbilityTemplate(AbilityTemplate abilityTemplate) {
        abilityTemplate.setName(nameLabel.getText());
        abilityTemplate.setConditions(conditionsLabel.getText());
        abilityTemplate.setDescription(descriptionLabel.getText());
    }
    
}
