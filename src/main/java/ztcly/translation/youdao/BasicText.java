package ztcly.translation.youdao;

import java.util.List;

public class BasicText {
    public String phonetic;
    public List<String> explains=null;

    public List<String> getExplains() {
        return explains;
    }

    public void setExplains(List<String> explains) {
        this.explains = explains;
    }

    public List<String> getExam_type() {
        return exam_type;
    }

    public void setExam_type(List<String> exam_type) {
        this.exam_type = exam_type;
    }

    public List<String> exam_type=null;

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }


}
