/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "tb_m_status_asset")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatusAsset.findAll", query = "SELECT s FROM StatusAsset s")
    , @NamedQuery(name = "StatusAsset.findById", query = "SELECT s FROM StatusAsset s WHERE s.id = :id")
    , @NamedQuery(name = "StatusAsset.findByName", query = "SELECT s FROM StatusAsset s WHERE s.name = :name")})
public class StatusAsset implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<AssetDetail> assetDetailList;

    public StatusAsset() {
    }

    public StatusAsset(String id) {
        this.id = id;
    }

    public StatusAsset(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<AssetDetail> getAssetDetailList() {
        return assetDetailList;
    }

    public void setAssetDetailList(List<AssetDetail> assetDetailList) {
        this.assetDetailList = assetDetailList;
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
        if (!(object instanceof StatusAsset)) {
            return false;
        }
        StatusAsset other = (StatusAsset) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.assetmanagement.entities.StatusAsset[ id=" + id + " ]";
    }
    
}
