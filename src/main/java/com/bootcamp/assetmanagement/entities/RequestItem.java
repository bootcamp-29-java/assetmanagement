/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "tb_tr_request_item")
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "update_request_item",
            procedureName = "sp_update_return_asset",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "iditem", type = String.class)
            }
    )
})
@NamedQueries({
    @NamedQuery(name = "RequestItem.findAll", query = "SELECT r FROM RequestItem r")
    , @NamedQuery(name = "RequestItem.findById", query = "SELECT r FROM RequestItem r WHERE r.id = :id")
    , @NamedQuery(name = "RequestItem.findByReturnDate", query = "SELECT r FROM RequestItem r WHERE r.returnDate = :returnDate")
    , @NamedQuery(name = "RequestItem.findByRequest", query = "SELECT r FROM RequestItem r WHERE r.request = :request")})
public class RequestItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "request")
    private String request;
    @JoinColumn(name = "asset", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AssetDetail asset;

    public RequestItem() {
    }

    public RequestItem(Integer id) {
        this.id = id;
    }

    public RequestItem(Integer id, String request) {
        this.id = id;
        this.request = request;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public AssetDetail getAsset() {
        return asset;
    }

    public void setAsset(AssetDetail asset) {
        this.asset = asset;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequestItem)) {
            return false;
        }
        RequestItem other = (RequestItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.assetmanagement.entities.RequestItem[ id=" + id + " ]";
    }
    
}
