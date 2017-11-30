package ch.steponline.portfolio.model;

import ch.steponline.core.model.Currency;
import ch.steponline.core.model.VersionedEntity;
import ch.steponline.identity.model.User;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "Portfolio", schema = "portfolio",
        uniqueConstraints = {@UniqueConstraint(columnNames = "No")},
    indexes = {@Index(
                name = "IDX_Portfolio_Name",
                columnList = "Name"),
                @Index(
                        name = "IDX_Portfolio_No",
                        columnList = "No"),
                @Index(
                        name = "IDX_Portfolio_Type",
                        columnList = "TypeId"),
                @Index(
                        name = "IDX_Portfolio_RiskCategory",
                        columnList = "RiskCategoryId"),
                @Index(
                        name = "IDX_Portfolio_ValidDate",
                        columnList = "ValidFrom, ValidTo"),
                @Index(
                        name = "IDX_Portfolio_ReferenceCurrency",
                        columnList = "ReferenceCurrencyId"),
                @Index(
                        name = "IDX_Portfolio_StrategyId",
                        columnList = "StrategyId")
        }
)
@Audited
@AuditTable(value="Portfolio", schema = "audit")
@org.hibernate.annotations.FilterDefs(value = {
        @FilterDef(name = "EvalDate", parameters = {@ParamDef(name = "evalDate", type = "date")}),
        @FilterDef(name = "ActualDate", parameters = {@ParamDef(name = "actualDate", type = "date")}),
        @FilterDef(name = "PerformanceStartDate", parameters = {@ParamDef(name = "performanceStartDate", type = "date")})
}
)
@NamedQueries(value = {
        @NamedQuery(name = "ValidPortfoliosByPortfolioNo", query = "select distinct pof from Portfolio pof where pof.validFrom<=:evalDate and (pof.validTo is null or pof.validTo>=:evalDate) and pof.no in (:portfolioNos)"),
        @NamedQuery(name = "ListPortfoliosWithPortfolioNo", query = "select p from Portfolio p where p.no = :portfolioNo and p.id <> :portfolioId"),
        @NamedQuery(name = "ListPortfoliosWithSimilaryPortfolioNo", query = "select p from Portfolio p where lower(p.no) like :portfolioNo and p.id <> :portfolioId"),
        @NamedQuery(name = "NoOfPortfoliosWithStrategyGroup", query = "select count(*) from Portfolio p where p.strategy = :strategy"),
        @NamedQuery(name = "PortfolioAccessAllowed", query = "select p from Portfolio p join p.allowedUsers a where p.id=:portfolioId and a.id=:userId")
}
)
public class Portfolio extends VersionedEntity{
    @Id
    @GeneratedValue(generator="portfolio_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="portfolio_seq",schema = "portfolio",sequenceName = "Portfolio_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the portfolio",example="1",position = 1)
    private Long id;

    @Column(name="validFrom", nullable = false)
    private Date validFrom;

    @Column(name="validTo",nullable = true)
    private Date validTo;

    @Column(name="No",nullable = false,unique = true)
    private String no;
    @Column(name="Name",nullable = false)
    private String name;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="ReferenceCurrencyId",referencedColumnName = "Id")
    private Currency referenceCurrencyId;

    /* This is a redundancy of the IsoAlphabetic of the Domain from referenceCurrencyId
    and it has to be actualized if the ReferenceCurrencyId is changed
     */
    @Column(name="ReferenceCurrency",nullable = false)
    private String referenceCurrency;

    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name="TypeId",referencedColumnName = "Id")
    private PortfolioType type;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @OptimisticLock(excluded = true)
    @JoinColumn(name="StrategyId",referencedColumnName = "Id")
    private Strategy strategy;

    @ManyToOne(optional=false,fetch=FetchType.EAGER)
    @JoinColumn(name="RiskCategoryId",referencedColumnName = "Id")
    private RiskCategory riskCategory;

    @Column(name="TotalAmount",nullable = true)
    private BigDecimal totalAmount;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "portfolio")
    private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "referencedPortfolio")
    private Set<Simulation> simulations=new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PortfolioUserAccess",
            joinColumns = {@JoinColumn(name = "PortfolioId")},
            inverseJoinColumns = {@JoinColumn(name = "UserId")}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @NotAudited
    private List<User> allowedUsers = new ArrayList<User>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public RiskCategory getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(RiskCategory riskCategory) {
        this.riskCategory = riskCategory;
    }

    public Currency getReferenceCurrencyId() {
        return referenceCurrencyId;
    }

    public void setReferenceCurrencyId(Currency referenceCurrencyId) {
        this.referenceCurrencyId = referenceCurrencyId;
        setReferenceCurrency(referenceCurrencyId.getIsoCode());
    }

    public String getReferenceCurrency() {
        return referenceCurrency;
    }

    public void setReferenceCurrency(String referenceCurrency) {
        this.referenceCurrency = referenceCurrency;
    }

    public PortfolioType getType() {
        return type;
    }

    public void setType(PortfolioType type) {
        this.type = type;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public Set<Simulation> getSimulations() {
        return simulations;
    }

    public void setSimulations(Set<Simulation> simulations) {
        this.simulations = simulations;
    }

    public List<User> getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(List<User> allowedUsers) {
        this.allowedUsers = allowedUsers;
    }
}
