package mariana.springbackend.share.dto;

import java.io.Serializable;

/***
 * Class in order to Create or Update a Post
 */
public class PostCreationDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private String content;

    private long exposureId;

    private int expirationTime;

    private String userEmail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getExposureId() {
        return exposureId;
    }

    public void setExposureId(long exposureId) {
        this.exposureId = exposureId;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
