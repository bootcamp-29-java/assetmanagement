/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "tb_tr_asset_detail")
@XmlRootElement
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "insert_detail_asset",
            procedureName = "sp_add_detail_asset",
            parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "name", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "datein", type = Date.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "penaltycost", type = Integer.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "asset", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "status", type = String.class)
            }
    )
})
@NamedQueries({
    @NamedQuery(name = "AssetDetail.findAll", query = "SELECT a FROM AssetDetail a")
    , @NamedQuery(name = "AssetDetail.findById", query = "SELECT a FROM AssetDetail a WHERE a.id = :id")
    , @NamedQuery(name = "AssetDetail.findByName", query = "SELECT a FROM AssetDetail a WHERE a.name = :name")
    , @NamedQuery(name = "AssetDetail.findByDateIn", query = "SELECT a FROM AssetDetail a WHERE a.dateIn = :dateIn")
    , @NamedQuery(name = "AssetDetail.findByPenaltyCost", query = "SELECT a FROM AssetDetail a WHERE a.penaltyCost = :penaltyCost")})
public class AssetDetail implements Serializable {

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
    @Column(name = "date_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "penalty_cost")
    private int penaltyCost;
    @OneToMany(mappedBy = "asset", fetch = FetchType.LAZY)
    private List<RequestItem> requestItemList;
    @JoinColumn(name = "room", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StatusAsset status;
    @JoinColumn(name = "asset", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Asset asset;
    @JoinColumn(name = "supplier", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Supplier supplier;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asset", fetch = FetchType.LAZY)
    private List<Cart> cartList;

    public AssetDetail() {
    }

    public AssetDetail(String id) {
        this.id = id;
    }

    public AssetDetail(String id, String name, int penaltyCost) {
        this.id = id;
        this.name = name;
        this.penaltyCost = penaltyCost;
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

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public int getPenaltyCost() {
        return penaltyCost;
    }

    public void setPenaltyCost(int penaltyCost) {
        this.penaltyCost = penaltyCost;
    }

    @XmlTransient
    public List<RequestItem> getRequestItemList() {
        return requestItemList;
    }

    public void setRequestItemList(List<RequestItem> requestItemList) {
        this.requestItemList = requestItemList;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public StatusAsset getStatus() {
        return status;
    }

    public void setStatus(StatusAsset status) {
        this.status = status;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @XmlTransient
    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
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
        if (!(object instanceof AssetDetail)) {
            return false;
        }
        AssetDetail other = (AssetDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.bootcamp.assetmanagement.entities.AssetDetail[ id=" + id + " ]";
    }
    
}
