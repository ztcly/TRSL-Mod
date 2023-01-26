package ztcly.translation.youdao;


//import java.util.ArrayList;
import java.util.List;

public class Output {
    public String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<String> getWeb() {
        return web;
    }

    public void setWeb(List<String> web) {
        this.web = web;
    }

    public BasicText getBasic() {
        return basic;
    }

    public void setBasic(BasicText basic) {
        this.basic = basic;
    }
    public String getExplains(){
        if(isWord){
            if(this.basic.explains!=null){
                return this.basic.explains.toString();
            }
            return "NULL";
        }
        return "NA";
    }
    public String getExamType(){
        if(isWord){
            if(this.basic.exam_type!=null){
                return this.basic.exam_type.toString();
            }
            return "NULL";
        }
        return "NA";
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public boolean isWord;
    public String query;
    public List<String> translation;
    public List<String> web;
    public BasicText basic=null;


}
