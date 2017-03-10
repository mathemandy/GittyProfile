package mathemandy.tselebro.com.gittyprofile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import mathemandy.tselebro.com.gittyprofile.Utility.HashUtils;

/**
 * Created by mathemandy on 09 Mar 2017.
 */
public class ProfileItem implements Parcelable {


    private int id;
    private String avatarPath;
    private String profileUrl;
    private String username;


    public ProfileItem(int id, String avatarPath, String username, String profileUrl) {
        this.id = id;
        this.avatarPath = avatarPath;
        this.username = username;
        this.profileUrl = profileUrl;
    }

    private ProfileItem(Parcel in) {
        id = in.readInt();
        avatarPath = in.readString();
        profileUrl = in.readString();
        username = in.readString();
    }



    public String getImportedhashCode(){
        StringBuilder builder = new StringBuilder();

        builder.append("id").append(id == 0 ? "" : id )
                .append("login").append(username == null ? "" : username)
                .append("avatar_url").append(profileUrl == null ? "" : profileUrl)
                .append("html_url").append(avatarPath == null? "" : avatarPath);

        return HashUtils.computeWeakHash(builder.toString());

    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**  Getters and setters
     *
     *
     */


    public String getUsername() {
        return  username;

    }


    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(avatarPath);
        parcel.writeString(profileUrl);
        parcel.writeString(username);
            }

    public static final Creator<ProfileItem> CREATOR = new Creator<ProfileItem>() {
        @Override
        public ProfileItem createFromParcel(Parcel in) {
            return new ProfileItem(in);
        }

        @Override
        public ProfileItem[] newArray(int size) {
            return new ProfileItem[size];
        }
    };



}
