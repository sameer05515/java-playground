package com.coding.practice.codes.transform.treedata.indentedString;

import com.coding.practice.codes.common.InvalidInputException;
import com.coding.practice.codes.transform.treedata.common.LineInfoPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.coding.practice.codes.transform.treedata.common.InputConstants.invalidIndentation;
import static com.coding.practice.codes.transform.treedata.common.InputConstants.invalidIndentation2;
import static com.coding.practice.codes.transform.treedata.common.InputConstants.sampleValidTreeInput;
import static com.coding.practice.codes.transform.treedata.common.InputConstants.stepInput;
import static com.coding.practice.codes.transform.treedata.common.InputConstants.vanshawali;

public class TraditionalApproach {

    public static void main(String[] args) {
        String[] arr={
                invalidIndentation,
                stepInput,
                sampleValidTreeInput,
                vanshawali,
                invalidIndentation2
        };
        for(String inp:arr){
            System.out.println("\n==================\n start: "+inp);
            try {
                transformToTree(inp);
            } catch (InvalidInputException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("\n########################\n end: "+inp);
        }

    }

    private static void transformToTree(String input) throws InvalidInputException {
        int step = 0;
        log("Validating input text: it should be non-null and not empty", ++step);
        if (input == null || input.trim().isEmpty()) {
            log("Validation Failed: Input is missing or empty", ++step);
            throw new InvalidInputException("Input is missing or empty");
        }
        log("Input is a valid string", ++step);
        log("Creating line array rom given input text, by splitting '\\n'", ++step);
        String[] lines = input.split("\\n");
        log("Total Raw Lines: " + lines.length, ++step);
        List<String> nonEmptyLines = new ArrayList<>();
        for (String line : lines) {
            if (line != null && !line.trim().isEmpty()) {
                nonEmptyLines.add(line);
            }
        }
        log("Total non-empty Lines: " + nonEmptyLines.size(), ++step);

        List<LineInfoPojo> maps;
        log("Get Line Text And Indentation Map from non-empty Lines: ", ++step);
        maps = getLineTextAndIndentationMap(nonEmptyLines, ++step);
        log("Created: Line Text And Indentation Map from non-empty Lines: " + maps, ++step);

        log("Get List of Map of object, having field, name, uniqueId, level : ", ++step);
        maps = getLevelNameUniqueIdAndChildrenMap(maps, ++step);

        log("Received Result: Get List of Map of object, having field, name, uniqueId, level : " + maps.stream()
                .map(lineInfo -> forLoggingLineInfoPojo.apply(lineInfo))
                .collect(Collectors.toList()), ++step);
        log("Get List of Map of object, filled with parentId and children: " + maps, ++step);
        maps= fillWithChildren(maps,null, ++step);
        log("Received Result: Get List of Map of object, having field, name, uniqueId, level : " + maps.stream()
                .map(lineInfo -> forLoggingTreeHierarchy.apply(lineInfo))
                .collect(Collectors.toList()), ++step);
    }

    private static final Function<LineInfoPojo, String> forLoggingTreeHierarchy =
            lineInfo -> formatLineInfoPojo(lineInfo, "\t");

    private static String formatLineInfoPojo(LineInfoPojo lineInfo, String prefix) {
        return "\n"+prefix
//                +"{"
                + "name: " + lineInfo.getName()
                + (lineInfo.getChildren()!=null && lineInfo.getChildren().size()>0?(
                " , children: " + lineInfo.getChildren().stream()
                        .map(ch->formatLineInfoPojo(ch, prefix+"\t"))
                        .collect(Collectors.joining(", ", "[", "]"))
                ):"")
//                + "}"
                ;
    }

    private static List<LineInfoPojo> fillWithChildren(List<LineInfoPojo> maps,String parentId, int step) {
        List<LineInfoPojo> result;

        List<LineInfoPojo> ss;
        ss=maps.stream().filter(m->m.getParentId()==parentId).collect(Collectors.toList());

//        log("Get List of Map of object, filled with parentId: '"+parentId+"' and children: " + ss, step);
        for (LineInfoPojo parent:ss){
            List<LineInfoPojo> children=fillWithChildren(maps,parent.getUniqueId(),step);
            if(children!=null && children.size()>0){
                parent.setChildren(children);
            }
        }

        result=ss;
        return result;

    }

    private static final Function<LineInfoPojo, String> forLoggingLineInfoPojo =
            lineInfo -> "\n{"
                    + "name: " + lineInfo.getName()
                    + " , indentationLevel: " + lineInfo.getIndentationLevel()
                    + " , diffWithPrevious: " + lineInfo.getDiffWithPrevious()
                    +" , relativeLevel: "+lineInfo.getRelativeLevel()
                    +" , level: "+lineInfo.getLevel()
                    +" , parentName: "+lineInfo.getParentName()
                    + "} ";

    private static List<LineInfoPojo> getLevelNameUniqueIdAndChildrenMap(List<LineInfoPojo> maps, int step) {
        log("""
            1. Find firstNonZeroIndentation, if after traversal, firstNonZeroIndentation is still 0, then return map
            2. Else if firstNonZeroIndentation has some non-zero value, then validate differences of indentationLevel and calculate level
            1. Validation before ' calculate level' :
              - Validate if there is inconsistency in indentation:
                - find first non zero, positive indentationLevel difference
                  - if firstNonZeroIndentation is
                - validate if other differences are multiple of 'firstDiff' 
                    and if positive multiple, then multiplier should not greater than 1
            """, step);

        int firstDiff = 0;
        boolean firstDiffFound = false;

        for (int x = 1; x < maps.size(); x++) {
            LineInfoPojo prevObject = maps.get(x - 1);
            LineInfoPojo currObj = maps.get(x);
            int diff = currObj.getIndentationLevel() - prevObject.getIndentationLevel();

            if (diff != 0 && !firstDiffFound) {
                firstDiff = diff;
                firstDiffFound = true;
            }

            if (firstDiffFound && firstDiff < 0) {
                throw new IllegalArgumentException(
                        "Invalid indentation found. Indentation should increment or decrement in multiples of the first difference. obj: " + currObj);
            }

            if(firstDiffFound && diff%firstDiff!=0){
                throw new IllegalArgumentException(
                        "Invalid indentation found. Indentation should increment or decrement in multiples of the first difference. obj: " + currObj);
            }

            currObj.setDiffWithPrevious(diff);
        }

        if (firstDiffFound) {
            for (int x = 1; x < maps.size(); x++) {
                LineInfoPojo prevPojo = maps.get(x - 1);
                LineInfoPojo pojo = maps.get(x);
                int relativeLevel = pojo.getDiffWithPrevious() / firstDiff;
                pojo.setRelativeLevel(relativeLevel);

                if (relativeLevel == 0) {
                    pojo.setParentId(prevPojo.getParentId());
                    pojo.setParentName(prevPojo.getParentName());
                    pojo.setLevel(prevPojo.getLevel());
                } else if (relativeLevel > 0) {
                    pojo.setParentId(prevPojo.getUniqueId());
                    pojo.setParentName(prevPojo.getName());
                    pojo.setLevel(prevPojo.getLevel() + 1);
                } else {
                    // Navigate upwards through ancestors to find the appropriate parent
                    int counter = Math.abs(relativeLevel);
                    LineInfoPojo parentPojo = prevPojo;
                    while (counter > 0 && parentPojo != null) {
                        String parentId = parentPojo.getParentId();
                        parentPojo = maps.stream()
                                .filter(m -> m.getUniqueId().equals(parentId))
                                .findFirst()
                                .orElse(null);
                        counter--;
                    }
                    if (parentPojo != null) {
                        pojo.setParentId(parentPojo.getParentId());
                        pojo.setParentName(parentPojo.getParentName());
                        pojo.setLevel(parentPojo.getLevel() + 1);
                    } else {
                        throw new IllegalArgumentException("Parent not found for object: " + pojo);
                    }
                }
            }
        }
        return maps;
    }




    private static List<LineInfoPojo> getLineTextAndIndentationMap(List<String> nonEmptyLines, int step) {
        log("""
                Validate if there is no mixed indentation:
                a. find first Indentation character ( space or tab)
                b. check this if all lines have indentation with same 'first Indentation character'
                c. If mixed indentation found, throw exception
                d. else return true
                """, step);
        char firstIndentationCharacter = '\n';
        boolean firstIndentationCharacterFound = false;
        for (String line : nonEmptyLines) {
            char[] chars = line.toCharArray();
            for (char c : chars) {
                if (c != ' ' && c != '\t') {
                    break;
                } else {
                    firstIndentationCharacter = c;
                    firstIndentationCharacterFound = true;
                    break;
                }
            }
            if (firstIndentationCharacterFound) break;
        }

        List<LineInfoPojo> lineInfoList = new ArrayList<>();
        for (String line : nonEmptyLines) {
            char[] chars = line.toCharArray();
            int indentationLevel = 0;

            for (char c : chars) {
                if (c == firstIndentationCharacter) {
                    indentationLevel++;
                } else if (c == ' ' || c == '\t') {
                    throw new IllegalArgumentException("Mixed indentation found in the line: " + line);
                } else {
                    break;
                }
            }

            LineInfoPojo lineInfo = LineInfoPojo.builder()
                    .name(line.trim())
                    .indentationLevel(indentationLevel)
                    .uniqueId(UUID.randomUUID().toString())
                    .build();
            lineInfoList.add(lineInfo);
        }

        return lineInfoList;
    }

    private static final boolean  shouldLog = true;
    private static final int allowedStep = 14;

    private static void log(String message, int step) {
        if (!shouldLog || step < allowedStep) return;
        System.out.println("Step: " + step + " , Message: " + message);
    }
}
