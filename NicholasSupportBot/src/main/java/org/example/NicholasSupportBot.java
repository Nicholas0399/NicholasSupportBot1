package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NicholasSupportBot extends TelegramLongPollingBot {

    private final Map<Long, Boolean> userStartedMap = new HashMap<>();
    private final Map<Long, String> userNameMap = new HashMap<>();

    @Override
    public String getBotUsername() {
        return "NicholasSupport_bot";
    }

    @Override
    public String getBotToken() {
        return "7390756964:AAE8olK2f3DxAr9Q5dv4EWE_q-6sIK_S9Jc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleIncomingMessage(update.getMessage().getChatId(), update.getMessage().getText(), update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleIncomingMessage(long chatId, String messageText, Message message) {
        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(chatId));

        if (messageText.equals("/start")) {
            userStartedMap.put(chatId, true);
            String welcomeMessage = "👋 Приветствую вас, дорогие друзья!\n\n" +
                    "Я — NicholasSupport Bot, созданный самим Nicholas'ом, чтобы быть вашим надежным проводником и отличной поддержкой. Готов помочь вам в любых вопросах и задачах, предоставляя необходимую информацию. " +
                    "Помимо этого, стоит отметить, что я нахожусь на стадии развития, и каждый день становлюсь всё лучше и более полезным для вас.\n\n" +
                    "Нажмите кнопку \"Привет!\", чтобы продолжить.";
            response.setText(welcomeMessage);
            response.setReplyMarkup(createHelloButton());
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (userStartedMap.getOrDefault(chatId, false) && !userNameMap.containsKey(chatId)) {
            userNameMap.put(chatId, messageText);
            String personalizedMessage = "Приятно познакомиться, " + messageText + "! 😊\n" +
                    "Я уверен, что мы с вами отлично подружимся. Давайте посмотрим, что я могу для вас сделать.";
            response.setText(personalizedMessage);
            response.setReplyMarkup(createMainMenuButton());
            userStartedMap.put(chatId, false);
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            response.setText("Используйте кнопку \"Привет!\", чтобы начать.");
            try {
                execute(response);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private InlineKeyboardMarkup createHelloButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton helloButton = new InlineKeyboardButton();
        helloButton.setText("👋 Привет!");
        helloButton.setCallbackData("HELLO");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(helloButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createMainMenuButton() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton mainMenuButton = new InlineKeyboardButton();
        mainMenuButton.setText("🏠 Главное меню");
        mainMenuButton.setCallbackData("MAIN_MENU");

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(mainMenuButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createMainMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton infoButton = new InlineKeyboardButton();
        infoButton.setText("ℹ️ Информация");
        infoButton.setCallbackData("INFO");

        InlineKeyboardButton socialButton = new InlineKeyboardButton();
        socialButton.setText("🌐 Соцсети");
        socialButton.setCallbackData("SOCIAL");

        InlineKeyboardButton gamesButton = new InlineKeyboardButton();
        gamesButton.setText("🎮 Игры");
        gamesButton.setCallbackData("GAMES");

        InlineKeyboardButton notificationsButton = new InlineKeyboardButton();
        notificationsButton.setText("🔔 Уведомления");
        notificationsButton.setCallbackData("NOTIFICATIONS");

        InlineKeyboardButton otherButton = new InlineKeyboardButton();
        otherButton.setText("📁 Другое");
        otherButton.setCallbackData("OTHER");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(infoButton);
        row1.add(socialButton);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(gamesButton);
        row2.add(notificationsButton);

        List<InlineKeyboardButton> row3 = new ArrayList<>();
        row3.add(otherButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);
        rows.add(row3);

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup createGamesMenu() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton pubgButton = new InlineKeyboardButton();
        pubgButton.setText("1. Pubg mobile");
        pubgButton.setCallbackData("PUBG");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(pubgButton);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(row1);

        for (int i = 2; i <= 10; i++) {
            InlineKeyboardButton emptyButton = new InlineKeyboardButton();
            emptyButton.setText(i + ". Пусто");
            emptyButton.setCallbackData("EMPTY_" + i);
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(emptyButton);
            rows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(rows);

        return inlineKeyboardMarkup;
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        if (callbackData.equals("HELLO")) {
            SendMessage helloMessage = new SendMessage();
            helloMessage.setChatId(String.valueOf(chatId));
            helloMessage.setText("Отлично, рад приветствовать вас! 😊\n" +
                    "Давайте начнем с того, как мне к вам обращаться. Как я могу называть вас?");
            helloMessage.setReplyMarkup(null);
            try {
                execute(helloMessage);
                EditMessageText removeHelloButtonMessage = new EditMessageText();
                removeHelloButtonMessage.setChatId(String.valueOf(chatId));
                removeHelloButtonMessage.setMessageId(messageId);
                removeHelloButtonMessage.setText(callbackQuery.getMessage().getText());
                removeHelloButtonMessage.setReplyMarkup(null);
                execute(removeHelloButtonMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (callbackData.equals("MAIN_MENU")) {
            SendMessage mainMenuMessage = new SendMessage();
            mainMenuMessage.setChatId(String.valueOf(chatId));
            mainMenuMessage.setText("🏠 Главное меню:");
            mainMenuMessage.setReplyMarkup(createMainMenu());
            try {
                execute(mainMenuMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (callbackData.equals("GAMES")) {
            SendMessage gamesMenuMessage = new SendMessage();
            gamesMenuMessage.setChatId(String.valueOf(chatId));
            gamesMenuMessage.setText("🎮 Игры:");
            gamesMenuMessage.setReplyMarkup(createGamesMenu());
            try {
                execute(gamesMenuMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (callbackData.equals("PUBG")) {
            // Информация об аккаунте PUBG Mobile
            String pubgStats = getPubgStats(); // Получаем информацию об аккаунте PUBG Mobile
            SendPhoto pubgPhotoMessage = new SendPhoto();
            pubgPhotoMessage.setChatId(String.valueOf(chatId));
            InputFile photoFile = new InputFile("https://i.postimg.cc/cC42CjL7/brgame-screenshot-20240527160458.jpg"); // Укажите URL фотографии или используйте File
            pubgPhotoMessage.setPhoto(photoFile); // Устанавливаем фото с помощью объекта InputFile
            pubgPhotoMessage.setCaption(pubgStats); // Добавляем к фото текст

            try {
                execute(pubgPhotoMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (callbackData.startsWith("EMPTY_")) {
            // Обработка пустых кнопок (если нужно)
        }
    }

    private String getPubgStats() {
        // Возвращение фиксированной информации
        String playerName = "Nicholas";
        String playerId = "51476062890";
        String clanName = "VillainsZI";

        return "Информация об аккаунте Pubg mobile:\n\n" +
                "👤 Имя пользователя: " + playerName + "\n" +
                "🆔 ID: " + playerId + "\n" +
                "🏅 Клан: " + clanName + "\n" +
                "🗺️ Любимая карта: Erangel\n" +
                "🔫 Любимое оружие: M416";
    }
}
