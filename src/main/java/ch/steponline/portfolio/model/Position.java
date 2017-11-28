package ch.steponline.portfolio.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Position", schema = "portfolio")
@Audited
@AuditTable(value="Position", schema = "audit")
public class Position {
    @Id
    @GeneratedValue(generator="position_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="position_seq",schema = "portfolio",sequenceName = "Position_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the position",example="1",position = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OptimisticLock(excluded = true)
    private Portfolio portfolio;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    private Asset asset;

    @Column(name="Nominal",nullable = false)
    private Double nominal;

    // The following Columns are just redundancies to get the needed Data faster
    @Column(name="FXRate",nullable=false)
    private Double fxRate;

    @Column(name="Currency",nullable=false)
    private String currency;

    @Column(name="MarketValue",nullable = false)
    private Double marketValue;

    @Column(name="MaturityDate",nullable = true)
    private LocalDate maturityDate;

    @Column(name="Isin",nullable = true)
    private String isin;

    @Column(name="ValorNo",nullable = true)
    private String valorNo;

    @Column(name="AssetName")
    private String assetName;
}

