/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtractorFromFacebook;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.*;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timme
 */
public class GetData {

    public GetData() {
        FileWriter writer = null;
        try {
            String accessToken = "EAAPml0w8uScBAOoTqZBGSOZBJZA6Xnhh8DwiScDFDIgxzeggEErplPKezNZBXnZCmikZC9ZCfrZASQnv75V0C1V3KehLdtKZCAweiPDTr9JTMboGZCji8dc5F2BpUyl7S3eyH0JqVyzG748W49jsGApIUlo0w0IWYM6N8ZD";
            FacebookClient fbClient = new DefaultFacebookClient(accessToken);
            String time = "", id, likes, comments, shares;
            writer = new FileWriter("C:\\temp\\daten.csv");
            writer.append("POST_ID");
            writer.append("~");
            writer.append("MESSAGE");
            writer.append("~");
            writer.append("LIKES_COUNT");
            writer.append("~");
            writer.append("SHARES_COUNT");
            writer.append("~");
            writer.append("COMMENT_COUNT");
            writer.append("~");
            writer.append("CREATED_TIME");
            writer.append("~");
            writer.append("\n");
            Connection<Post> result = fbClient.fetchConnection("SPDStuhr/feed", Post.class);
            for (List<Post> page : result) {
                for (Post post : page) {
                    String message = post.getMessage();
                    if (message != null) {
                        Date date = post.getCreatedTime();
                        int year = date.getYear() + 1900;
                        int month = date.getMonth();
                        int day = date.getDate();
                        int hour = date.getHours();
                        int min = date.getMinutes();
                        int sec = date.getSeconds();
                        time = day + "." + month + "." + year + " " + hour + ":" + min + ":" + sec;
                        id = post.getId();
                        post = fbClient.fetchObject(id, Post.class, Parameter.with("fields", "message, reactions.summary(true),comments.summary(true),shares.summary(true)"));
                        shares = post.getSharesCount().toString();
                        comments = post.getCommentsCount().toString();
                        writer.append("fb.com/" + id);
                        writer.append("~");

                        while (message.contains("\n")) {
                            message = message.replaceAll("\\n", " ");
                        }

                        writer.append(message);
                        System.out.println(message);
                        writer.append("~");

                        likes = post.getReactionsCount().toString();
                        System.out.println(likes);
                        writer.append(likes);
                        writer.append("~");

                        writer.append(shares);
                        writer.append("~");
                        writer.append(comments);
                        writer.append("~");
                        writer.append(time);
                        writer.append("~");
                        writer.append("\n");
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

//                    System.out.println("fb.com/" + post.getId());
//                    System.out.println("Message: " + message);
//                    System.out.println("Likes count: " + post.getLikesCount());
//                    System.out.println("Shares count: " + post.getSharesCount());
//                    System.out.println("Comments count: " + post.getCommentsCount());
//                    Connection<Comment> allComments = fbClient.fetchConnection(post.getId() + "/comments", Comment.class);
//                    for (List<Comment> postcomments : allComments) {
//                        for (Comment comment : postcomments) {
//                            System.out.println(comment.getMessage());
//                        }
//                    }
//                    System.out.println("");
//                    System.out.println("");
//                    counter++;
//                    likes += post.getLikesCount();
//                    shares += post.getSharesCount();
//                    com += post.getCommentsCount();
//                }
//            }
//            System.out.println("SPD Stuhr: ");
//            System.out.println("Anzahl Post: " + counter);
//            System.out.println("Anzahl Likes: " + likes);
//            System.out.println("Anzahl Comments: " + com);
//            System.out.println("Anzahl Shares: " + shares);
//            System.out.println("");
//            System.out.println("");
