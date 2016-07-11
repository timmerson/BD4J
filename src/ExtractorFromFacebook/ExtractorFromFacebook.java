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
import com.restfb.types.Post;
import java.util.List;

/**
 *
 * @author timme
 */
public class ExtractorFromFacebook {

    public ExtractorFromFacebook() {
        String accessToken = "EAAPml0w8uScBAOoTqZBGSOZBJZA6Xnhh8DwiScDFDIgxzeggEErplPKezNZBXnZCmikZC9ZCfrZASQnv75V0C1V3KehLdtKZCAweiPDTr9JTMboGZCji8dc5F2BpUyl7S3eyH0JqVyzG748W49jsGApIUlo0w0IWYM6N8ZD";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        int counter = 0, likes = 0, com = 0, shares = 0;

        Connection<Post> result = fbClient.fetchConnection("SPDStuhr/feed", Post.class);
        for (List<Post> page : result) {
            for (Post post : page) {
//                String message = post.getMessage();
                post = fbClient.fetchObject(post.getId(), Post.class, Parameter.with("fields", "from,to,likes.limit(0).summary(true),comments.limit(0).summary(true),shares.limit(0).summary(true)"));
//                System.out.println("fb.com/" + post.getId());
//                System.out.println("Message: " + message);
//                System.out.println("Likes count: " + post.getLikesCount());
//                System.out.println("Shares count: " + post.getSharesCount());
//                System.out.println("Comments count: " + post.getCommentsCount());
//                System.out.println("");
//                System.out.println("");
                counter++;
                likes += post.getLikesCount();
                shares += post.getSharesCount();
                com += post.getCommentsCount();
            }
        }
        System.out.println("SPD Stuhr: ");
        System.out.println("Anzahl Post: " + counter);
        System.out.println("Anzahl Likes: " + likes);
        System.out.println("Anzahl Comments: " + com);
        System.out.println("Anzahl Shares: " + shares);
        System.out.println("");
        System.out.println("");
    }
}
