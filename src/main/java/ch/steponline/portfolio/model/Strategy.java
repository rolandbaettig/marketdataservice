package ch.steponline.portfolio.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name="Strategy", schema = "portfolio")
@Audited
@AuditTable(value="Strategy", schema = "audit")
public class Strategy {
    @Id
    @GeneratedValue(generator="strategy_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="strategy_seq",schema = "portfolio",sequenceName = "Strategy_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the strategy",example="1",position = 1)
    private Long id;
}
