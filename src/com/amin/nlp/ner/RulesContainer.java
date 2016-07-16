package com.amin.nlp.ner;

import com.amin.nlp.dictionary.NamedEntityTag;
import com.amin.nlp.pos.PosTag;
import com.amin.nlp.utils.TextUtils;
import com.amin.nlp.word.Word;
import com.amin.nlp.word.WordBoundaryTag;

/**
 * Created by Amin on 6/30/2016.
 */
public class RulesContainer {

    public static final Rule RULE_FOR_DISEASE = new Rule() {

        @Override
        public void apply(ContextInfo contextInfo) {
            Word targetWord = contextInfo.getTargetWord();
            if (contextInfo.getContextWordAt(-1) != null) {
                Word w1m = contextInfo.getContextWordAt(-1);
                if (w1m.getLemma().equals("\u0648\u0631\u0645") || // varam
                        w1m.getStem().equals("\u0648\u0631\u0645") || // varam
                        w1m.getLemma().equals("\u062a\u0648\u0631\u0645") || // tavarrom
                        w1m.getStem().equals("\u062a\u0648\u0631\u0645") || // tavarrom
                        w1m.getLemma().equals("\u0632\u062e\u0645") || // zakhm
                        w1m.getStem().equals("\u0632\u062e\u0645") || // zakhm
                        w1m.getLemma().equals("\u0639\u0641\u0648\u0646\u062a") || // ofoonat
                        w1m.getStem().equals("\u0639\u0641\u0648\u0646\u062a") || // ofoonat
                        w1m.getLemma().equals("\u0633\u0648\u0632\u0634") || // soozesh
                        w1m.getStem().equals("\u0633\u0648\u0632\u0634") || // soozesh
                        w1m.getLemma().equals("\u062f\u0631\u062f") || // dard
                        w1m.getStem().equals("\u062f\u0631\u062f") || // dard
                        w1m.getLemma().equals("\u0628\u064A\u0645\u0627\u0631\u064A") || // bimari
                        w1m.getStem().equals("\u0628\u064A\u0645\u0627\u0631\u064A") || // bimari
                        w1m.getLemma().equals("\u0645\u0631\u064A\u0636\u064A") || // marizi
                        w1m.getStem().equals("\u0645\u0631\u064A\u0636\u064A") || // marizi
                        w1m.getLemma().equals("\u0627\u0645\u0631\u0627\u0636") || // amraz
                        w1m.getStem().equals("\u0627\u0645\u0631\u0627\u0636") || // amraz
                        w1m.getLemma().equals("\u0646\u0627\u0631\u0627\u062d\u062a\u064A") || // narahati
                        w1m.getStem().equals("\u0646\u0627\u0631\u0627\u062d\u062a\u064A") || // narahati
                        w1m.getLemma().equals("\u0646\u0627\u0631\u0633\u0627\u064A\u064A") || // naresayi
                        w1m.getStem().equals("\u0646\u0627\u0631\u0633\u0627\u064A\u064A") || // naresayi
                        w1m.getLemma().equals("\u0633\u0631\u0637\u0627\u0646") || // saratan
                        w1m.getStem().equals("\u0633\u0631\u0637\u0627\u0646")) { // saratan

                    w1m.setNamedEntityTag(NamedEntityTag.DISEASE);
                    w1m.setExtra(w1m.getLemma() + TextUtils.SPACE + targetWord.getExtra());
                    w1m.setBoundaryTag(WordBoundaryTag.BEGINNING);
                    w1m.setCoherentNextWord(targetWord);

                    targetWord.setNamedEntityTag(NamedEntityTag.DISEASE);
                    targetWord.setExtra(w1m.getLemma() + TextUtils.SPACE + targetWord.getExtra());
                    targetWord.setBoundaryTag(WordBoundaryTag.INSIDE);

                    Word coherentWord = targetWord;
                    while (coherentWord.getCoherentNextWord() != null) {
                        coherentWord = coherentWord.getCoherentNextWord();
                        coherentWord.setNamedEntityTag(NamedEntityTag.DISEASE);
                        coherentWord.setExtra(w1m.getLemma() + TextUtils.SPACE + targetWord.getExtra());
                        coherentWord.setBoundaryTag(WordBoundaryTag.INSIDE);
                    }
                    return;
                }
            }
            if (contextInfo.getContextWordAt(1) != null) {
                Word w1p = contextInfo.getContextWordAt(1);
                if (w1p.getLemma().equals("\u062f\u0631\u062f") || // dard
                        w1p.getStem().equals("\u062f\u0631\u062f")) { // dard

                    targetWord.setNamedEntityTag(NamedEntityTag.DISEASE);
                    targetWord.setExtra(targetWord.getExtra() + TextUtils.SPACE + w1p.getLemma());
                    targetWord.setBoundaryTag(WordBoundaryTag.BEGINNING);
                    targetWord.setCoherentNextWord(w1p);

                    w1p.setNamedEntityTag(NamedEntityTag.DISEASE);
                    w1p.setExtra(targetWord.getExtra() + TextUtils.SPACE + w1p.getLemma());
                    w1p.setBoundaryTag(WordBoundaryTag.INSIDE);
                    return;
                }
            }
        }
    };

    public static final Rule RULE_FOR_NATURE = new Rule() {

        @Override
        public void apply(ContextInfo contextInfo) {
            Word targetWord = contextInfo.getTargetWord();
            if (targetWord.getLemma().equals("\u062a\u0631") || // tar
                    targetWord.getLemma().equals("\u062e\u0634\u06a9") || // khoshk
                    targetWord.getLemma().equals("\u0633\u0631\u062f") || // sard
                    targetWord.getLemma().equals("\u06af\u0631\u0645")) { // garm

                if (targetWord.getExtra().equals(targetWord.getLemma())) {
                    if (contextInfo.getContextWordAt(-1) != null) {
                        Word w1m = contextInfo.getContextWordAt(-1);
                        if (w1m.getLemma().equals("\u0645\u0632\u0627\u062c") || // mezaj
                                w1m.getStem().equals("\u0645\u0632\u0627\u062c") || // mezaj
                                w1m.getLemma().equals("\u0637\u0628\u0639") || // tab'e
                                w1m.getStem().equals("\u0637\u0628\u0639") || // tab'e
                                w1m.getLemma().equals("\u0637\u0628\u064A\u0639\u062a") || // tabi'at
                                w1m.getStem().equals("\u0637\u0628\u064A\u0639\u062a")) {  // tabi'at

                            return; // it's ok, for example mezaj e tar
                        } else {
                            targetWord.setNamedEntityTag(null);
                            targetWord.setExtra(null);
                            targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                            return;
                        }
                    } else {
                        targetWord.setNamedEntityTag(null);
                        targetWord.setExtra(null);
                        targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                        return;
                    }
                } else {
                    return; // it's ok, for example garm va khoshk
                }
            }
            if (targetWord.getLemma().equals("\u0645\u0639\u062a\u062f\u0644")) { // motadel
                if (contextInfo.getContextWordAt(-1) != null) {
                    Word w1m = contextInfo.getContextWordAt(-1);
                    if (w1m.getLemma().equals("\u0645\u0632\u0627\u062c") || // mezaj
                            w1m.getStem().equals("\u0645\u0632\u0627\u062c") || // mezaj
                            w1m.getLemma().equals("\u0637\u0628\u0639") || // tab'e
                            w1m.getStem().equals("\u0637\u0628\u0639") || // tab'e
                            w1m.getLemma().equals("\u0637\u0628\u064A\u0639\u062a") || // tabi'at
                            w1m.getStem().equals("\u0637\u0628\u064A\u0639\u062a")) {  // tabi'at

                        return; // it's ok
                    }
                    if (w1m.getLemma().equals("\u0646\u0627")) { // nA
                        if (contextInfo.getContextWordAt(-2) != null) {
                            Word w2m = contextInfo.getContextWordAt(-2);
                            if (w2m.getLemma().equals("\u0645\u0632\u0627\u062c") || // mezaj
                                    w2m.getStem().equals("\u0645\u0632\u0627\u062c") || // mezaj
                                    w2m.getLemma().equals("\u0637\u0628\u0639") || // tab'e
                                    w2m.getStem().equals("\u0637\u0628\u0639") || // tab'e
                                    w2m.getLemma().equals("\u0637\u0628\u064A\u0639\u062a") || // tabi'at
                                    w2m.getStem().equals("\u0637\u0628\u064A\u0639\u062a")) {  // tabi'at

                                w1m.setNamedEntityTag(NamedEntityTag.NATURE);
                                w1m.setExtra(w1m.getLemma() + targetWord.getLemma());
                                w1m.setBoundaryTag(WordBoundaryTag.BEGINNING);
                                w1m.setCoherentNextWord(targetWord);

                                targetWord.setNamedEntityTag(NamedEntityTag.NATURE);
                                targetWord.setExtra(w1m.getLemma() + targetWord.getLemma());
                                targetWord.setBoundaryTag(WordBoundaryTag.INSIDE);
                                return;
                            } else {
                                w1m.setNamedEntityTag(null);
                                w1m.setExtra(null);
                                w1m.setBoundaryTag(WordBoundaryTag.OUTSIDE);

                                targetWord.setNamedEntityTag(null);
                                targetWord.setExtra(null);
                                targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                                return;
                            }
                        }
                        targetWord.setNamedEntityTag(null);
                        targetWord.setExtra(null);
                        targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                        return; // na motadel in first of text
                    }
                    targetWord.setNamedEntityTag(null);
                    targetWord.setExtra(null);
                    targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                    return; // na motadel in first of text
                } else {
                    targetWord.setNamedEntityTag(null);
                    targetWord.setExtra(null);
                    targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                    return; // motadel in first of text
                }
            }
            if (targetWord.getLemma().equals("\u0646\u0627\u0645\u0639\u062a\u062f\u0644")) {// namotadel
                if (contextInfo.getContextWordAt(-1) != null) {
                    Word w1m = contextInfo.getContextWordAt(-1);
                    if (w1m.getLemma().equals("\u0645\u0632\u0627\u062c") || // mezaj
                            w1m.getStem().equals("\u0645\u0632\u0627\u062c") || // mezaj
                            w1m.getLemma().equals("\u0637\u0628\u0639") || // tab'e
                            w1m.getStem().equals("\u0637\u0628\u0639") || // tab'e
                            w1m.getLemma().equals("\u0637\u0628\u064A\u0639\u062a") || // tabi'at
                            w1m.getStem().equals("\u0637\u0628\u064A\u0639\u062a")) {  // tabi'at

                        return; // it's ik
                    } else {
                        targetWord.setNamedEntityTag(null);
                        targetWord.setExtra(null);
                        targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                        return;
                    }
                } else {
                    targetWord.setNamedEntityTag(null);
                    targetWord.setExtra(null);
                    targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
                    return;
                }
            }
        }
    };

    public static final Rule RULE_FOR_DISAMBIGUATING_BEH = new Rule() {

        @Override
        public void apply(ContextInfo contextInfo) {
            Word targetWord = contextInfo.getTargetWord();
            boolean flag = false;
            if (contextInfo.getContextWordAt(-2) != null && contextInfo.getContextWordAt(-1) != null) {
                Word w2m = contextInfo.getContextWordAt(-2);
                Word w1m = contextInfo.getContextWordAt(-1);
                String s2m = "\u0645\u064A\u0648\u0647"; // miveh
                String s1m = "\u064A"; // farsi ye
                if (w2m.getLemma().equals(s2m) && w1m.getLemma().equals(s1m)) {
                    flag = true;
                }
            }
            if (targetWord.getPosTag() == PosTag.P || targetWord.getPosTag() == PosTag.N) {
                if (contextInfo.getContextWordAt(-1) != null && contextInfo.getContextWordAt(-1).getPosTag() == PosTag.P) {
                    flag = true;
                }
                if (contextInfo.getContextWordAt(1) != null && contextInfo.getContextWordAt(1).getPosTag() == PosTag.P) {
                    flag = true;
                }
            }
            if (contextInfo.getContextWordAt(1) != null) {
                String daraye = "\u062f\u0627\u0631\u0627\u064A"; // daraye
                if (contextInfo.getContextWordAt(1).getLemma().equals(daraye)) {
                    flag = true;
                }
            }

            if (flag) {
                targetWord.setNamedEntityTag(NamedEntityTag.HERB);
                targetWord.setExtra(targetWord.getLemma());
                targetWord.setBoundaryTag(WordBoundaryTag.BEGINNING);
            } else {
                targetWord.setNamedEntityTag(null);
                targetWord.setExtra(null);
                targetWord.setBoundaryTag(WordBoundaryTag.OUTSIDE);
            }
        }
    };

//    public static final Rule mRuleForProvince = new Rule() {
//
//        @Override
//        public void apply(ContextInfo contextInfo) {
//            Word targetWord = contextInfo.getTargetWord();
//            if (targetWord.getPosTag() == PosTag.N) {
//                if (contextInfo.getContextWordAt(-1) != null) {
//                    Word w = contextInfo.getContextWordAt(-1);
//                    String s = "\u0627\u0633\u062a\u0627\u0646"; // ostan
//                    if (w.getLemma().equals(s) || w.getStem().equals(s)) {
//                        targetWord.setNamedEntityTag(NamedEntityTag.LOCATION_IRAN_PROVINCE);
//                        targetWord.setBoundaryTag(WordBoundaryTag.BEGINNING);
//                    }
//                }
//            }
//        }
//    };
}
