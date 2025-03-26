package ru.nightmare.diplomny.model;

import lombok.*;
import ru.nightmare.diplomny.entity.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
/**
 * TestProceccor
 * Так хорошо, чё этот класс должен делать.
 * Этот класс модель, статичный вызов условной бизнес логики сие приложения.
 * Он, только считает результаты и говорят чё можно чё нельзя.
 * Выдаёт результаты, но сам ничего не меняет(на момент написания временно пишет в сами объекты,
 * правда дальше оно никуда не идёт, скорее всего на момент,
 * как вы это читаете это уже не так и я не посчитал нужным это удалять).
 * Не взаимодействует с базой данных и какими-либо АПИ, он СОЛО ЛОГИКА АБСТРАКЦИЯ.
 * Никакие поля тут менять НЕЛЬЗЯ.
 * НИЧЕГО НЕЛЬЗЯ ДОПИСЫВАТЬ.
 * Так чё ещё я хочу.
 * А да
 * У НЕГО НЕТ ЗАЩИТЫ, ПРОЕБЕШЬСЯ С ВЫЗОВОМ ВЫДАСТ НУЛПОИНТЕР И ХУЯК ПРОГРАММУ.
 * Всё, вот с такими условиями можно и переходить к написанию.
 */

public class TestProcessor {
    private final TestUser previous;
    private final TestUser current;
    private final TestInstanceRedirection[] instanceRedirection;
    private final TestParameter[] parameter;
    private final TestAnswerReward[] reward;
    private final TestAnswer[] answer;
    @Getter
    private final TestResult[] result;
    private final TestResult[] prevTestResult; // нужен для рассчета может ли пользователь после текущего теста идти дальше, если нет ему нужно вернуться на тест назад
    private final TestParameter[] prevTestParameter; // нужен для рассчета может ли пользователь после текущего теста идти дальше, если нет ему нужно вернуться на тест назад

    /*
        Ой крч шота тяжко надо логику сначала описать
        Все эти абстракции ей богу выживут меня из ума когда-нибудь, но это всяко лучше какого-нибудь там пхпшки.


        Пользователь не переходит к тесту и тест считается не пройденным если предыдущий вообще не пройден

        Далее тест отклоняется если по какому-то из параметров пользователь не проходит предыдущий тест вплане наследуемых условий,
        но пользователь может его просто пройти и не пойдёт дальше пока не улучшит результат предыдущего.
        По умолчанию оно будет равно нулю если в предыдущем тесте оно не было указано.
        То есть тест проходится по всем результатам с предыдущего и выдаёт ответ для текущего(можно/нельзя) сразу же.

        Далее чё надо...  А, я наверное сделаю выдачу результатов тоже статичной, сразу по созданию посчитать и в файнал переменные вывесить, вот, красота будет.

        Так шо ещё... Такая-же таблица с параметр=баллы, мы проходимся по каждому ответу, сразу же снимаем с перенаправления настоящий ответ и пишем его к сумме.
     */
    {
        process();
    }
    public Map<Integer, Integer> score;

    @Getter
    public String message = "";
    @Getter
    private Boolean previousIsNotPassed = false;
    @Getter
    private Boolean failedByResults = false;
    @Getter
    private Boolean failedByPreviousTestLowResults = false;
    public void process() {
        if (isAllowedToPass()) {
            score = new HashMap<>();
            for (TestInstanceRedirection redirection : instanceRedirection) {

                for (TestAnswerReward reward : reward) {
                    if(reward.getTestAnswerID()==answer[redirection.getRedirectedToNumber()].getTestAnswerID()) {
                        if(score.containsKey(reward.getParameterID()))
                            score.put(reward.getParameterID(), reward.getValue());
                        else
                            score.put(reward.getParameterID(), score.get(reward.getParameterID())+reward.getValue());
                    }
                }
            }
            boolean passed = true;// Проверка прохождения по параметрам, если хоть один завален не проходишь
            for (TestParameter par :
                    parameter) { // Проходим по параметрам
                if(score.get(par.getTestParameterID())!=null) {// Защита от дурака
                    for(TestResult res: result) {// Проходимся по всем результатам
                        if(res.getTestParameterID()==par.getTestParameterID()) {// Если айди один и тот же
                            res.setSummary(score.get(par.getTestParameterID()));// Пишем результат для дальнейшей работы
                            // Надо проверить прохождение параметров плинтусу
                            if(passed&&score.get(par.getTestParameterID())<par.getRequired()) {
                                passed = false;
                                current.setPassed(false);
                                failedByResults = true;
                                message = "Ваши результаты не прошли заявленный уровень, пожалуйста повторите попытку";
                            }

                        }
                    }
                    // Заодно проверить как дела поживают относительно теста родителя
                    if(par.getPreviousRequired()!=0) { // Проверка заданности условия, должно иметь такое же имя
                        for (TestParameter prev :
                                parameter) {
                            if (par.getName().equals(prev.getName())) {
                                for(TestResult prevRes: prevTestResult) {
                                    if(prevRes.getSummary()<par.getPreviousRequired()) {
                                        current.setPassed(false);
                                        failedByPreviousTestLowResults = true;
                                        message = "Результаты предыдущего теста не удовлетворительно сочетаются с данным, пожалуйста получить результат выше";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            message = "Ой, кажется ты ещё не прошёл предыдущий тест, пожалуйста пройди сначала его";
            current.setPassed(false);
            previousIsNotPassed = true;
        }
        if(!previousIsNotPassed && !failedByResults && !failedByPreviousTestLowResults) {
            current.setPassed(true);
            message = "Поздравляем! Вы прошли тест!";
        }
    }

    public boolean isPassed() {return !previousIsNotPassed && !failedByResults && !failedByPreviousTestLowResults;}
    public boolean isAllowedToPass() {
        return previous == null || previous.getPassed();
    }

    public boolean isRequirementsPassed() {
        return false;
    }
}
