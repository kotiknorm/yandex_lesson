package background.mblz.yandex.com.yandexlesson.provider;

import com.google.gson.Gson;
import background.mblz.yandex.com.yandexlesson.model.SingerInfo;
import retrofit.Callback;

/**
 * Created by Andrey on 08.04.2016.
 */
public class TestSingerInfoProvider implements ISingerInfoProvider{

    private static final String listJson = "[\n" +
            "  {\n" +
            "    \"id\": 1080505,\n" +
            "    \"name\": \"Tove Lo\",\n" +
            "    \"genres\": [\n" +
            "      \"pop\",\n" +
            "      \"dance\",\n" +
            "      \"electronics\"\n" +
            "    ],\n" +
            "    \"tracks\": 81,\n" +
            "    \"albums\": 22,\n" +
            "    \"link\": \"http://www.tove-lo.com/\",\n" +
            "    \"description\": \"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.\",\n" +
            "    \"cover\": {\n" +
            "      \"small\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300\",\n" +
            "      \"big\": \"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2915,\n" +
            "    \"name\": \"Ne-Yo\",\n" +
            "    \"genres\": [\n" +
            "      \"rnb\",\n" +
            "      \"pop\",\n" +
            "      \"rap\"\n" +
            "    ],\n" +
            "    \"tracks\": 256,\n" +
            "    \"albums\": 152,\n" +
            "    \"link\": \"http://www.neyothegentleman.com/\",\n" +
            "    \"description\": \"обладатель трёх премии Грэмми, американский певец, автор песен, продюсер, актёр, филантроп. В 2009 году журнал Billboard поставил Ни-Йо на 57 место в рейтинге «Артисты десятилетия».\",\n" +
            "    \"cover\": {\n" +
            "      \"small\": \"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/300x300\",\n" +
            "      \"big\": \"http://avatars.yandex.net/get-music-content/15ae00fc.p.2915/1000x1000\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 91546,\n" +
            "    \"name\": \"Usher\",\n" +
            "    \"genres\": [\n" +
            "      \"rnb\",\n" +
            "      \"pop\",\n" +
            "      \"rap\"\n" +
            "    ],\n" +
            "    \"tracks\": 450,\n" +
            "    \"albums\": 183,\n" +
            "    \"link\": \"http://usherworld.com/\",\n" +
            "    \"description\": \"американский певец и актёр. Один из самых коммерчески успешных R&B-музыкантов афроамериканского происхождения. В настоящее время продано более 65 миллионов копий его альбомов по всему миру. Выиграл семь премий «Грэмми», четыре премии World Music Awards, четыре премии American Music Award и девятнадцать премий Billboard Music Awards. Владелец собственной звукозаписывающей компании US Records. Он занимает 21 место в списке самых успешных музыкантов по версии Billboard, а также второе место, уступив Эминему в списке самых успешных музыкантов 2000-х годов. В 2010 году журнал Glamour включил его в список 50 самых сексуальных мужчин.\",\n" +
            "    \"cover\": {\n" +
            "      \"small\": \"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/300x300\",\n" +
            "      \"big\": \"http://avatars.yandex.net/get-music-content/b0e14f75.p.91546/1000x1000\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 100500,\n" +
            "    \"name\": \"Jay Sean\",\n" +
            "    \"genres\": [\n" +
            "      \"pop\",\n" +
            "      \"rap\",\n" +
            "      \"rnb\"\n" +
            "    ],\n" +
            "    \"tracks\": 106,\n" +
            "    \"albums\": 38,\n" +
            "    \"description\": \"британский рэпер, являющийся выходцем из Индии. Родился в западном Лондоне, Англия. Выпустил три альбома Me Against Myself, My Own Way и All or Nothing.\",\n" +
            "    \"cover\": {\n" +
            "      \"small\": \"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/300x300\",\n" +
            "      \"big\": \"http://avatars.yandex.net/get-music-content/db35e57a.p.100500/1000x1000\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 74614,\n" +
            "    \"name\": \"Kelly Rowland\",\n" +
            "    \"genres\": [\n" +
            "      \"rnb\",\n" +
            "      \"pop\",\n" +
            "      \"rap\"\n" +
            "    ],\n" +
            "    \"tracks\": 174,\n" +
            "    \"albums\": 106,\n" +
            "    \"link\": \"http://www.kellyrowland.com/\",\n" +
            "    \"description\": \"американская певица и актриса. Выступает в стиле современный ритм-энд-блюз, является автором текстов песен.\",\n" +
            "    \"cover\": {\n" +
            "      \"small\": \"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/300x300\",\n" +
            "      \"big\": \"http://avatars.yandex.net/get-music-content/be7f0f49.p.74614/1000x1000\"\n" +
            "    }\n" +
            "  }]";

    @Override
    public void getAllSingers(Callback<SingerInfo[]> response) {
        Gson gson = new Gson();
        SingerInfo[] singerInfos = gson.fromJson(listJson, SingerInfo[].class);
        response.success(singerInfos, null);
    }
}
