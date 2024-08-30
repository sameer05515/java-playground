package com.coding.practice.codes.transform.treedata.common;

public interface InputConstants {
    String stepInput= """
                        
            Steps to process an indented String to transform it into Tree Data
            	1. Validate Input String
            		1.1 Ensure input is a string
                        
            		1.2 Validate the string contains lines of text
            			1.2.1 Trim each line of unnecessary whitespace
            			1.2.2 Filter out any empty lines
                        
            	2. Determine Indentation Levels
            		2.1 Calculate indentation level for each line
            		2.2 Store indentation levels in an array
                        
            	3. Validate Indentation Consistency
                        
            		3.1 Check if all indentation levels are equal
            		3.1.1 If equal, create a flat hierarchy with level 0
            		3.2 Calculate the difference between consecutive indentation levels
                        
            		3.3 Validate consistency of indentation differences
            		3.3.1 If inconsistent, return an error
                        
            	4. Build Tree Structure
            		4.1 Compute levels for each line based on indentation
            		4.2 Append each line with its computed level and initialize children array
            		4.3 Fill in parent IDs for each line based on computed levels
            		4.4 Transform the flat structure into a hierarchical tree
                        
            	5. Return Tree Data
            		5.1 If all steps are successful, return the tree data
            		5.2 If any step fails, return the corresponding error message
            """;

    String sampleValidTreeInput="""
            1
                1.1
                1.2
                1.3
                
                
            2
                2.1
                2.2
                2.3
                    2.3.1
                        2.3.1.1
                    2.3.2
            3
                3.1
                
                
            """;

    String sampleFlatInput="Hello world\nThis is a sample text\nJava 8 is powerful";

    String invalidIndentation= """
                    Two Tabs
                OneTab
                    Root no 2
                        Root ki aulad
            """;
    String invalidIndentation2= """
                root
                        grandchildren
                    child
                    child2
            """;

    String vanshawali= """
            Bikram Sah
                Sitaram Sah
                    Rajender Sah
                        Omprakash
                    Mahender Sah
                    Shankar Sah
                    Chotan Sah
                Chandu Sah
                    Bhola Prasad
                        Premendra Kumar
                        Narendra Kumar
                            Nandini
                        Rimjhim Kumari
                            Shaarvi
                    Koshalya Devi
                        Poonam Devi
                        Ravi Ranjan
                        Rajeev Ranjan
                Nagina Sah
                Mangal Prasad
            
            Krishna Prasad
                Vijay Kumar
                Vinay Kumar
                Chiranjivi Lal
                Kanhaiya Prasad
            """;
}
