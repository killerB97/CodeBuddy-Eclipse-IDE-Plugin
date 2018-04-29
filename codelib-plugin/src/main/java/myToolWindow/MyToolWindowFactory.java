package myToolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class MyToolWindowFactory implements ToolWindowFactory {

    private JPanel myToolWindowContent;
    private JTabbedPane tabbedPane1;
    private JTextField queryTextField;
    private JTextField methodNameTextField;
    private JCheckBox analyseCodeCheckBox;
    private JTextField paremetersTextField;
    private JButton searchButton;
    private JTextArea programTextArea;
    private JTextField queryStackoverflow;
    private JButton searchStackoverflowButton;
    private JTextArea titleTextArea;
    private JTextArea questionTextArea;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JTextField postTextField;
    private JButton postButton;
    private JButton fetchFromGitHubButton;
    private JButton likeButton;
    private JButton dislikeButton;
    private JButton nextButton;
    private JButton previousButton;
    private JTextArea discussionTextArea;
    private JButton nextDiscussionButton;
    private JButton previousDiscussionButton;
    private JTextField codeURL;
    private JTextField discussionURL;
    private JButton openCodeURL;
    private JButton openDiscussionURL;
    private ToolWindow myToolWindow;

    private List<List<String>> fetchedSolutionsAndUrls = new ArrayList<>();
    private List<String> fetchedSolutions = new ArrayList<>();
    private List<String> fetchedUrls = new ArrayList<>();
    private int solutionNumber = 1;

    private List<List<String>> discussionResults = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<String> questions = new ArrayList<>();
    private List<String> discussions = new ArrayList<>();
    private List<String> discussionUrls = new ArrayList<>();
    private int discussionNumber = 1;


    public MyToolWindowFactory() {

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = queryTextField.getText();
                if (query != null) {
                    fetchedSolutionsAndUrls = CodeGenerator.fetchCode(query);
                    fetchedSolutions = fetchedSolutionsAndUrls.get(0);
                    fetchedUrls = fetchedSolutionsAndUrls.get(1);
                }


                if (fetchedSolutions.size() != 0) {
                    programTextArea.setText(fetchedSolutions.get(0));
                    codeURL.setText(fetchedUrls.get(0));
                } else {
                    programTextArea.setText("No solution found!");
                }


            }
        });

        fetchFromGitHubButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = queryTextField.getText();
                if (query != null) {
                    query += " gist.github";
                    fetchedSolutionsAndUrls = CodeGenerator.fetchCode(query);
                    fetchedSolutions = fetchedSolutionsAndUrls.get(0);
                    fetchedUrls = fetchedSolutionsAndUrls.get(1);
                }

                if (fetchedSolutions.size() != 0) {
                    programTextArea.setText(fetchedSolutions.get(0));
                    codeURL.setText(fetchedUrls.get(0));
                } else {
                    programTextArea.setText("No solution found!");
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((solutionNumber + 1) <= fetchedSolutions.size()) {
                    programTextArea.setText(fetchedSolutions.get(solutionNumber));
                    codeURL.setText(fetchedUrls.get(solutionNumber));
                    solutionNumber += 1;
                } else {
                    programTextArea.setText("No more solutions!");
                    codeURL.setText("NA");
                }
            }
        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((solutionNumber - 1) <= fetchedSolutions.size()) {
                    if (solutionNumber > -1) {
                        programTextArea.setText(fetchedSolutions.get(solutionNumber - 1));
                        codeURL.setText(fetchedUrls.get(solutionNumber - 1));
                        solutionNumber -= 1;
                    }
                }
            }
        });

        openCodeURL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = codeURL.getText();
                    String command = "open " + url;
                    Runtime.getRuntime().exec(command);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });

        likeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fw = new FileWriter("/Users/dsv/Desktop/codelib-plugin/src/main/resources/myToolWindow/database/like.txt", true);
                    String query = queryTextField.getText();
                    String url = codeURL.getText();
                    String writeString = query + "," + url + "\n";
                    fw.write(writeString);
                    fw.close();
                } catch (Exception fileExcept) {
                    fileExcept.printStackTrace();
                }
            }
        });

        dislikeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileWriter fw = new FileWriter("/Users/dsv/Desktop/codelib-plugin/src/main/resources/myToolWindow/database/dislike.txt", true);
                    String query = queryTextField.getText();
                    String url = codeURL.getText();
                    String writeString = query + "," + url + "\n";
                    fw.write(writeString);
                    fw.close();
                } catch (Exception fileExcept) {
                    fileExcept.printStackTrace();
                }
            }
        });

        searchStackoverflowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = queryStackoverflow.getText();
                discussionResults = Stackoverflow.getStackOverflowDiscussions(query);
                titles = discussionResults.get(0);
                questions = discussionResults.get(1);
                discussions = discussionResults.get(2);
                discussionUrls = discussionResults.get(3);

                if (titles.size() != 0) {
                    titleTextArea.setText(titles.get(0));
                    questionTextArea.setText(questions.get(0));
                    discussionTextArea.setText(discussions.get(0));
                    discussionURL.setText(discussionUrls.get(0));
                } else {
                    titleTextArea.setText("No discussion found!");
                    questionTextArea.setText("No discussion found!");
                    discussionTextArea.setText("No discussion found!");
                }
            }
        });

        nextDiscussionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((discussionNumber + 1) <= titles.size()) {
                    titleTextArea.setText(titles.get(discussionNumber));
                    questionTextArea.setText(questions.get(discussionNumber));
                    discussionTextArea.setText(discussions.get(discussionNumber));
                    discussionURL.setText(discussionUrls.get(discussionNumber));
                    discussionNumber += 1;
                } else {
                    titleTextArea.setText("No more discussion found!");
                    questionTextArea.setText("No more discussion found!");
                    discussionTextArea.setText("No more discussion found!");
                    discussionURL.setText("NA");
                }
            }
        });

        previousDiscussionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((discussionNumber - 1) <= titles.size()) {
                    if (discussionNumber > -1) {
                        titleTextArea.setText(titles.get(discussionNumber - 1));
                        questionTextArea.setText(questions.get(discussionNumber - 1));
                        discussionTextArea.setText(discussions.get(discussionNumber - 1));
                        discussionURL.setText(discussionUrls.get(discussionNumber - 1));
                        discussionNumber -= 1;
                    }
                }
            }
        });

        openDiscussionURL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = discussionURL.getText();
                    String command = "open " + url;
                    Runtime.getRuntime().exec(command);
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });

    }

    //     Create the tool window content.
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        myToolWindow = toolWindow;
//        this.currentDateTime();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(myToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
