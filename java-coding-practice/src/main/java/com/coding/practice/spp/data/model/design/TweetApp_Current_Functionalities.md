# TweetApp Current Functionalities

__(To be listed)__

# TweetApp Current Application bugs

- **Interview Question module**
    - **1. takes 2-3 seconds to load initial data**
    - After saving,
        - newly created node is visible after 2-3 seconds
        - Newly created node is not being selected. In place of this, the old selected node remains selected
            - This makes confusion and some times, user starts adding answers to a wrong question.
        - If there is any validation or error came during save, no error/warning coming.
    - **2. Reduce number of mandatory fields**
        - If not necessary, no valiation should come for below while saving/updating a question/answer
            - Additional description
            - name,
            - heading,
            - rating ,
            - tags