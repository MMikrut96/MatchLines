package pl.edu.wat.kulki.pz.utils.database;

import javax.persistence.*;

@Entity
@Table(name = "highscore")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column
    String playerName;

    @Column
    String score;
}
