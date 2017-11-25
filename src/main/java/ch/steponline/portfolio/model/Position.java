package ch.steponline.portfolio.model;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

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
}
