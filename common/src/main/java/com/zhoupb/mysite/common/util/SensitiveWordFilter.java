package com.zhoupb.mysite.common.util;

import com.zhoupb.mysite.common.exception.CustomException;
import org.springframework.http.HttpStatus;

import java.util.*;

public class SensitiveWordFilter {

    private Map<Character, Map<?, ?>> transitionTable;

    @SuppressWarnings("unchecked ")
    public void buildTransitionTable(Set<String> sensitiveWords) {
        transitionTable = new HashMap<>();
        for (String word : sensitiveWords) {
            Map<Character, Map<?, ?>> currentMap = transitionTable;
            for (char c : word.toCharArray()) {
                currentMap.putIfAbsent(c, new HashMap<>());
                currentMap = (Map<Character, Map<?, ?>>) currentMap.get(c);
            }
            currentMap.put('\0', null);  // '\0' indicates the end of a sensitive word
        }
    }

    @SuppressWarnings("unchecked ")
    public Set<String> findSensitiveWords(String text) {
        if ( transitionTable == null || transitionTable.size() == 0 )
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "敏感词过滤器未初始化");
        Set<String> foundWords = new HashSet<>();
        Map<Character, Map<?, ?>> currentMap = transitionTable;
        StringBuilder currentWord = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (!currentMap.containsKey(c)) {
                currentMap = transitionTable;  // Reset to the initial state
                currentWord.setLength(0);      // Reset the currentWord
            } else {
                currentMap = (Map<Character, Map<?, ?>>) currentMap.get(c);
                currentWord.append(c);
                if (currentMap.containsKey('\0')) {
                    foundWords.add(currentWord.toString());
                }
            }
        }
        return foundWords;
    }
}
