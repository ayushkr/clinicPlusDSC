/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.srisrisri.clinic.scheduler;

/**
 *
 * @author akr2
 */
public interface Contactable {

    public abstract String getModule();

    public long getId();

    public String getName();

    public String getContactPhone();

}
