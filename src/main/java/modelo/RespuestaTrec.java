/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author israe
 */
public class RespuestaTrec {

    private String consulta;
    final private String tag;
    private String documento;
    private String ranking;
    private String score;
    final private String equipo;

    public RespuestaTrec() {
        this.tag = "Q0";
        this.equipo = "IFA";
    }

    public RespuestaTrec(String consulta, String documento, String ranking, String score) {
        this.consulta = consulta;
        this.tag = "Q0";
        this.documento = documento;
        this.ranking = ranking;
        this.score = score;
        this.equipo = "IFA";
    }

    public String getConsulta() {
        return consulta;
    }

    public void setConsulta(String consulta) {
        this.consulta = consulta;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    //Consulta Q0 documento ranking score EQUIPO is the format of the file
    @Override
    public String toString() {
        return "" + consulta + " " + tag + " " + documento + " " + ranking + " " + score + " " + equipo;
    }

}
