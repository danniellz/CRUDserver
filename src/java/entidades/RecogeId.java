/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Aritz Arrieta
 */
@Embeddable
public class RecogeId implements Serializable{
    private Integer trabajadorId;
    private Integer incidenciaId;
}
