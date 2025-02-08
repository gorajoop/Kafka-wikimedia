package joop.kafka.stream.wikimedia.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecentChangeEvent {
    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("type")
    private String type;

    @JsonProperty("namespace")
    private int namespace;

    @JsonProperty("title")
    private String title;

    @JsonProperty("title_url")
    private String titleUrl;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("timestamp")
    private long timestamp;

    @JsonProperty("user")
    private String user;

    @JsonProperty("bot")
    private boolean bot;

    @JsonProperty("log_id")
    private long logId;

    @JsonProperty("log_type")
    private String logType;

    @JsonProperty("log_action")
    private String logAction;

    //@JsonProperty("log_params")
    @JsonIgnore
    private LogParams logParams;

    @JsonProperty("log_action_comment")
    private String logActionComment;

    @JsonProperty("server_url")
    private String serverUrl;

    @JsonProperty("server_name")
    private String serverName;

    @JsonProperty("server_script_path")
    private String serverScriptPath;

    @JsonProperty("wiki")
    private String wiki;

    @JsonProperty("parsedcomment")
    private String parsedComment;

    private Map<String, Object> additionalFields = new HashMap<String, Object>();

    @JsonAnySetter
    public void setAdditionalField(String key, Object value) {
        additionalFields.put(key, value);
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalFiled(){
        return additionalFields;
    }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class LogParams {
    @JsonProperty("curid")
    private long curid;

    @JsonProperty("previd")
    private long previd;

    @JsonProperty("auto")
    private int auto;

    private Map<String, Object> additionalFields = new HashMap<String, Object>() ;

    @JsonAnySetter
    public void setAdditionalField(String key, Object value) {
        additionalFields.put(key, value);
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalFiled(){
        return additionalFields;
    }

}

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
class Meta {
    @JsonProperty("uri")
    private String uri;

    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("id")
    private String id;

    @JsonProperty("dt")
    private String dt;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("stream")
    private String stream;

    @JsonProperty("topic")
    private String topic;

    @JsonProperty("partition")
    private int partition;

    @JsonProperty("offset")
    private long offset;

    private Map<String, Object> additionalFields = new HashMap<>() ;

    @JsonAnySetter
    public void setAdditionalField(String key, Object value) {
        additionalFields.put(key, value);
    }
    @JsonAnyGetter
    public Map<String, Object> getAdditionalFiled(){
        return additionalFields;
    }

}




