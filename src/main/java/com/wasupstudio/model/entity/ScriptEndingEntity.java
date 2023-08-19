package com.wasupstudio.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 劇本資料表
 * @TableName script
 */
@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "wa_script_ending")
public class ScriptEndingEntity implements Serializable {

    @Id
    @Column(name = "script_id", nullable = false)
    private Integer scriptId;

    @Column(name = "advisory_time", nullable = false)
    private Integer advisoryTime;

    @Column(name = "ending_description")
    private String endingDescription;

    @Column(name = "ending_one", nullable = false)
    private String endingOne;
    @Column(name = "ending_two", nullable = false)
    private String endingTwo;
    @Column(name = "ending_three", nullable = false)
    private String endingThree;
    @Column(name = "ending_four", nullable = false)
    private String endingFour;

    @Column(name = "orderly_one", nullable = false)
    private Integer orderlyOne;
    @Column(name = "orderly_two", nullable = false)
    private Integer orderlyTwo;
    @Column(name = "orderly_three", nullable = false)
    private Integer orderlyThree;
    @Column(name = "orderly_four", nullable = false)
    private Integer orderlyFour;


    @Column(name = "relation_one", nullable = false)
    private Integer relationOne;
    @Column(name = "relation_two", nullable = false)
    private Integer relationTwo;
    @Column(name = "relation_three", nullable = false)
    private Integer relationThree;
    @Column(name = "relation_four", nullable = false)
    private Integer relationFour;
}

