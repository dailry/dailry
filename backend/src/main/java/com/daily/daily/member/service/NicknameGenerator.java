package com.daily.daily.member.service;

import com.daily.daily.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class NicknameGenerator {
    private static String[] firstNames = {"배고픈", "흉포한", "게으른", "잠자는", "배부른", "피곤한", "신나는", "행복한", "심심한", "지루한"};
    private static String[] lastNames = {"오랑우탄", "사자", "북극곰", "판다", "하늘다람쥐", "기린", "하마", "알파카", "사슴", "물개", "호랑이", "코끼리", "기러기", "치타", "캥거루"};

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    private final MemberRepository memberRepository;

    public String generateRandomNickname() {
        return generateNickname();
    }

    private String generateNickname() {
        int randomNumber = random.nextInt(9000) + 1000;
        String randomNickname = firstNames[random.nextInt(firstNames.length)] + lastNames[random.nextInt(lastNames.length)] + randomNumber;
        if (validateDuplicatedNickname(randomNickname)) {
            return generateNickname();
        }
        return randomNickname;
    }

    private boolean validateDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
