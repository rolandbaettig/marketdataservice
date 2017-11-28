package ch.steponline.portfolio.model;

import ch.steponline.core.model.Domain;
import ch.steponline.core.model.DomainRelation;

import javax.persistence.*;
import java.io.Serializable;


    @Entity
    @DiscriminatorValue("ASSET_TYPE")
    @NamedQueries(value = {
            @NamedQuery(name = "ValidAssetTypes",
                    query = "select distinct n from AssetType n " +
                            "join fetch n.textEntries tc " +
                            "where n.validFrom<=:evalDate and (n.validTo is null or n.validTo>=:evalDate) " +
                            "order by n.sortNo,n.isoAlphabetic"),
    })
    public class AssetType extends Domain implements Serializable {

        @Transient
        private AssetTypeGroup assetTypeGroup;

        public AssetTypeGroup getAssetTypeGroup() {
            if (assetTypeGroup==null) {
                for (DomainRelation rel : getToDomainRelations()) {
                    if (rel.getDomainRoleRelationDefinition().isAssetTypeGroupRelation()) {
                        assetTypeGroup=(AssetTypeGroup)rel.getDomainFrom();
                        break;
                    }
                }
            }
            return assetTypeGroup;
        }

    }
