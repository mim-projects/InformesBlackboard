package com.mimsoft.informesblackboard.application.controllers.web.common;

import com.mimsoft.informesblackboard.Configuration;
import com.mimsoft.informesblackboard.application.controllers.shared.RequestController;
import com.mimsoft.informesblackboard.application.utils.others.ArrayHelper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;

import java.text.SimpleDateFormat;
import java.util.*;

@Named("commonCtrl")
@RequestScoped
public class CommonController {
    @Inject
    private RequestController requestController;

    public void FacesMessage(FacesMessage.Severity severity, String title, String details) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, title, details));
        PrimeFaces.current().ajax().update("messages");
    }

    public void FacesMessagesError(String title, String details) {
        FacesMessage(FacesMessage.SEVERITY_ERROR, title, details);
    }

    public void FacesMessagesInfo(String title, String details) {
        FacesMessage(FacesMessage.SEVERITY_INFO, title, details);
    }

    public void FacesMessagesWarn(String title, String details) {
        FacesMessage(FacesMessage.SEVERITY_WARN, title, details);
    }

    public void CloseAndUpdate(String editWv, String formData) {
        PrimeFaces.current().ajax().update(formData);
        PrimeFaces.current().executeScript("PF('" + editWv + "').hide()");
    }

    public void showFacesMessageAjax() {
        try {
            String severityStr = requestController.getParam("severity");
            String title = requestController.getParam("title");
            String details = requestController.getParam("details");
            FacesMessage.Severity severity = severityStr == null ? FacesMessage.SEVERITY_INFO : severityStr.equalsIgnoreCase("warn") ? FacesMessage.SEVERITY_WARN : severityStr.equalsIgnoreCase("error") ? FacesMessage.SEVERITY_ERROR : FacesMessage.SEVERITY_INFO;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, title, details));
            PrimeFaces.current().ajax().update("messages");
        } catch (Exception ignore) {}
    }

    public String createRandomPassword(int alphanumericSize, int symbolsSize) {
        final String[] symbols = "^$?!@#%&".split("");
        final String[] alphanumeric = "0123456789abcdefghijklmnopqrstuvwxyz".split("");
        StringBuilder data = new StringBuilder();
        for (int k = 0; k < symbolsSize; k++) data.append(symbols[new Random().nextInt(symbols.length)]);
        for (int k = 0; k < alphanumericSize; k++) {
            String temp = alphanumeric[new Random().nextInt(alphanumeric.length)];
            if (new Random().nextInt(alphanumeric.length) % 2 == 0) temp = temp.toLowerCase();
            data.append(temp);
        }
        List<String> dataStr = new ArrayList<>(Arrays.asList(data.toString().split("")));
        Collections.shuffle(dataStr); Collections.shuffle(dataStr); Collections.shuffle(dataStr);
        StringBuilder result = new StringBuilder();
        for (String item: dataStr) result.append(item);
        return result.toString();
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public String getDateFormat(Date date, String format) {
        try { return new SimpleDateFormat(format).format(date); }
        catch (Exception ignore) { return ""; }
    }

    public String concat(String param, String comparator, Object value) {
        return param + comparator + value;
    }

    public String concatEquals(String param, Object value) {
        return concat(param, "=", value);
    }

    public String getCustomBadge(int id) {
        final String[] clazz = {"successful", "error", "firing", "read", "delivered", "resolved"};
        return clazz[id % clazz.length];
    }

    public List<Integer[]> arrAddAndRemove(Integer[] update, Integer[] original) {
        List<Integer[]> list = new ArrayList<>(2);
        List<Integer> create = new ArrayList<>();
        List<Integer> remove = new ArrayList<>();
        for (Integer select: update) {
            if (!new ArrayHelper<Integer>().contains(select, original)) {
                create.add(select);
            }
        }
        for (Integer select: original) {
            if (!new ArrayHelper<Integer>().contains(select, update)) {
                remove.add(select);
            }
        }
        list.add(create.toArray(new Integer[0]));
        list.add(remove.toArray(new Integer[0]));
        return list;
    }

    public Integer[] createArrayInteger(int size) {
        Integer[] arr = new Integer[size];
        for (int k = 0; k < size; k++) arr[k] = k;
        return arr;
    }

    public Integer createRandomNumber(Integer max) {
        return new Random().nextInt(max);
    }

    public String dateOfWeek(String getDataForBundle, Integer day) {
        String[] days = getDataForBundle.split(",");
        if (days.length != 7) return "";
        return days[day % days.length];
    }

    public String limitStr(String str, Integer size) {
        if (str == null || size < 1) return "-";
        return str.length() > size ?str.substring(0, size) :str;
    }

    public String replaceLastElementId(String completeCurrentId, String lastElementId) {
        String[] parts = completeCurrentId.split(":");
        String result = "";
        for (int k = 0; k < parts.length - 1; k++) result += parts[k] + ":";
        return result + lastElementId;
    }

    public String toUppercase(String message) {
        return message.toUpperCase();
    }

    public void showGlobalLoader() {
        PrimeFaces.current().executeScript("createGlobalLoader()");
    }

    public void hideGlobalLoader() {
        PrimeFaces.current().executeScript("removeGlobalLoader()");
    }

    public String[] listToArray(List<String> list) {
        String[] data = new String[list.size()];
        for (int k = 0; k < data.length; k++) data[k] = list.get(k);
        return data;
    }

    public String getCurrentCssTheme() {
        return Configuration.THEME_CSS;
    }
}
