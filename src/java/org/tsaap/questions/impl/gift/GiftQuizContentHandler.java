/*
 * Copyright 2013 Tsaap Development Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tsaap.questions.impl.gift;

import org.tsaap.questions.QuizContentHandler;
import org.tsaap.questions.TextFragment;
import org.tsaap.questions.impl.DefaultAnswer;
import org.tsaap.questions.impl.DefaultAnswerFragment;
import org.tsaap.questions.impl.DefaultQuestion;
import org.tsaap.questions.impl.DefaultQuiz;

/**
 * @author franck Silvestre
 */
public class GiftQuizContentHandler implements QuizContentHandler {

    private DefaultQuiz quiz;
    private DefaultQuestion currentQuestion;
    private DefaultAnswerFragment currentAnswerFragment;
    private DefaultAnswer currentAnswer;
    private StringBuffer currentTitle;

    /**
     * Get the quiz
     *
     * @return the quiz
     */
    public DefaultQuiz getQuiz() {
        return quiz;
    }


    /**
     * Receive notification of the beginning of a quiz
     */
    public void onStartQuiz() {
        quiz = new DefaultQuiz();
    }

    /**
     * Receive notification of the end of a quiz
     */
    public void onEndQuiz() {}

    /**
     * Receive notification of the beginning of a question
     */
    public void onStartQuestion() {
        currentQuestion = new DefaultQuestion();
    }

    /**
     * Receive notification of the end of a question
     */
    public void onEndQuestion() {
        quiz.addQuestion(currentQuestion);
        currentQuestion = null;
    }


    /**
     * Receive notification of the beginning of a title
     */
    public void onStartTitle() {
       currentTitle = new StringBuffer();
    }

    /**
     * Receive notification of the end of a title
     */
    public void onEndTitle() {
      currentQuestion.setTitle(currentTitle.toString());
        currentTitle = null;
    }

    /**
     * Receive notification of the beginning of an answer fragment
     */
    public void onStartAnswerFragment() {
        currentAnswerFragment = new DefaultAnswerFragment();
    }

    /**
     * Receive notification of the end of an answer fragment
     */
    public void onEndAnswerFragment() {
        currentQuestion.addAnswerFragment(currentAnswerFragment);
        currentAnswerFragment = null;
    }

    /**
     * Receive notification of the beginning of an answer
     */
    public void onStartAnswer() {
        currentAnswer = new DefaultAnswer();
    }

    /**
     * Receive notification of the end of an answer
     */
    public void onEndAnswer() {
        currentAnswerFragment.addAnswer(currentAnswer);
        currentAnswer = null;
    }

    /**
     * Receive notification of a new string
     *
     * @param str the received string
     */
    public void onString(final String str) {
      String trimedStr = str.trim();
      if (currentTitle != null) {
        currentTitle.append(trimedStr);
      } else if (currentAnswer != null) {
          currentAnswer.setTextValue(trimedStr.substring(1));
          if (trimedStr.startsWith("=")) {
              currentAnswer.setPercentCredit(100f);
          } else {
              currentAnswer.setPercentCredit(0f);
          }
      } else if (currentQuestion != null) {
          currentQuestion.addTextFragment(new TextFragment() {
              public String getText() {
                  return str;
              }
          });
      }
    }
}
