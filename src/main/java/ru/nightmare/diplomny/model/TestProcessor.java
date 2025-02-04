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
    private final Test test;
    private final TestInstanceRedirection[] instanceRedirection;
    private final TestParameter[] parameter;
    private final TestAnswerReward[] reward;
    private final TestAnswer[] answer;
    private final TestResult[] result;

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
    public void process() {
        if (isAllowedToPass()) {
            Map<Integer, Integer> score = new HashMap<>();
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
        } else {
            current.setPassed(false);
        }
    }

    public boolean isAllowedToPass() {
        return previous == null || previous.isPassed();
    }

    public boolean isRequirementsPassed() {
        return false;
    }
}
