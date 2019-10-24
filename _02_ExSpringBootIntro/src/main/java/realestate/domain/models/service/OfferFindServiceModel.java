package realestate.domain.models.service;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class OfferFindServiceModel {

    private String id;
    private BigDecimal familyBudget;
    private String familyApartmentType;
    private String familyName;

    public OfferFindServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    public BigDecimal getFamilyBudget() {
        return familyBudget;
    }

    public void setFamilyBudget(BigDecimal familyBudget) {
        this.familyBudget = familyBudget;
    }

    @NotNull
    @Size(min = 1)
    public String getFamilyApartmentType() {
        return familyApartmentType;
    }

    public void setFamilyApartmentType(String familyApartmentType) {
        this.familyApartmentType = familyApartmentType;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
