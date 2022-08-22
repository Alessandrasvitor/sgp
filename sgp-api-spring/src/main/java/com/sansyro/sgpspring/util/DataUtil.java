package com.sansyro.sgpspring.util;

import com.sansyro.sgpspring.exception.ServiceException;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class DataUtil {
    public static final int VALOR_ZERADO = 0;
    public static final long DIA_EM_MILESSEGUNDOS = 86400000L;
    public static final String FORMATO_DATA_PADRAO = "dd/MM/yyyy";

    public static final int QUANTIDADE_DIAS_EM_UMA_SEMANA = 7;
    public static final int QUANTIDADE_DIAS_EM_UMA_QUINZENA = 15;
    public static final int QUANTIDADE_DIAS_EM_UM_MES = 30;
    public static final int QUANTIDADE_DIAS_EM_UM_BIMESTRE = 60;
    public static final int QUANTIDADE_DIAS_EM_UM_TRIMESTRE = 90;
    public static final int QUANTIDADE_DIAS_EM_UM_QUADRIMESTRE = 120;
    public static final int QUANTIDADE_DIAS_EM_UM_SEMESTRE = 180;
    public static final int QUANTIDADE_DIAS_EM_UM_ANO = 365;
    /**
     * Mapa para representar cada período em número de dias.

    public static final Map<Integer, Integer> MAPA_PERIODO_EM_DIAS = Collections.unmodifiableMap(new HashMap<Integer, Integer>
            () {
        {
            put(TipoPeriodoPlanejamentoBaseEnum.DIARIO.getId(), DataUtil.UM_DIA);
            put(TipoPeriodoPlanejamentoBaseEnum.SEMANAL.getId(), QUANTIDADE_DIAS_EM_UMA_SEMANA);
            put(TipoPeriodoPlanejamentoBaseEnum.QUINZENAL.getId(), QUANTIDADE_DIAS_EM_UMA_QUINZENA);
            put(TipoPeriodoPlanejamentoBaseEnum.MENSAL.getId(), QUANTIDADE_DIAS_EM_UM_MES);
            put(TipoPeriodoPlanejamentoBaseEnum.BIMESTRAL.getId(), QUANTIDADE_DIAS_EM_UM_BIMESTRE);
            put(TipoPeriodoPlanejamentoBaseEnum.TRIMESTRAL.getId(), QUANTIDADE_DIAS_EM_UM_TRIMESTRE);
            put(TipoPeriodoPlanejamentoBaseEnum.QUADRIMESTRAL.getId(), QUANTIDADE_DIAS_EM_UM_QUADRIMESTRE);
            put(TipoPeriodoPlanejamentoBaseEnum.SEMESTRAL.getId(), QUANTIDADE_DIAS_EM_UM_SEMESTRE);
            put(TipoPeriodoPlanejamentoBaseEnum.ANUAL.getId(), QUANTIDADE_DIAS_EM_UM_ANO);
        }
    });*/

    /**
     * Previne instanciação direta da classe Util
     */
    private DataUtil() {
    }

    /**
     * Representa o valor de um ano
     */
    public static final int UM_ANO = 1;

    /**
     * Representa o valor de um dia
     */
    private static final int UM_DIA = 1;

    /**
     * Representa um resultado de comparação como menor.
     */
    public static final Integer VALOR_MENOR = -1;

    /**
     * Representa um resultado de comparação como igual.
     */
    public static final Integer VALOR_IGUAL = 0;

    /**
     * Representa um resultado de comparação como maior.
     */
    public static final Integer VALOR_MAIOR = 1;

    /**
     * Última hora do dia
     */
    public static final int HORA_23 = 23;

    /**
     * Último minuto do dia
     */
    public static final int MINUTO_59 = 59;

    /**
     * Último segundo do dia
     */
    public static final int SEGUNDO_59 = 59;

    /**
     * Último milisegundo do dia
     */
    public static final int MILISEGUNDO_999 = 999;

    /**
     * Compara duas datas.<br>
     * Verifica se a primeira é menor/igual/maior que a segunda.<br>
     * Permite ignorar o horário na comparação.<br>
     *
     * @param data1       {@link Date} - Primeira data a ser comparada
     * @param data2       {@link Date} - Segunda data a ser comparada
     * @param ignorarHora - Indica se a hora vai ser ignorada, ou seja, comparação só de                    data
     * @return {@link Integer} - Constante que indica o código do resultado
     */
    public static Integer compararData(Date data1, Date data2, boolean ignorarHora) {
        Date dataInicio = ObjectUtils.clone(data1);
        Date dataFim = ObjectUtils.clone(data2);

        if (ignorarHora) {
            dataInicio = retornaDataHoraZerada(data1);
            dataFim = retornaDataHoraZerada(data2);
        }

        return dataInicio.compareTo(dataFim);
    }

    /**
     * Método que retorna uma data com o horário zerado.<br>
     * Ex: <b>Thu Dec 02 00:00:00 BRST 2010</b><br>
     *
     * @param data - {@link Date}
     * @return {@link Date}
     */
    public static Date retornaDataHoraZerada(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, VALOR_ZERADO);
        c.set(Calendar.MINUTE, VALOR_ZERADO);
        c.set(Calendar.SECOND, VALOR_ZERADO);
        c.set(Calendar.MILLISECOND, VALOR_ZERADO);
        return c.getTime();
    }

    /**
     * Converte de LocalDate para Date.
     *
     * @param dataASerConvertida data a ser convertida
     * @return data Convertida em formato Date
     */
    public static Date converteParaDate(LocalDate dataASerConvertida) {
        return Date.from(dataASerConvertida.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converte de LocalDateTime para Date.
     *
     * @param dataASerConvertida data a ser convertida
     * @return data Convertida em formato Date
     */
    public static Date converteParaDate(LocalDateTime dataASerConvertida) {
        return converteParaDate(dataASerConvertida.toLocalDate());
    }

    /**
     * Converte de LocalDate para Date.
     *
     * @param dataASerConvertida data a ser convertida
     * @return data convertida para o tipo LocalDate
     */
    public static LocalDate converteDateParaLocalDate(Date dataASerConvertida) {
        Instant instant = Instant.ofEpochMilli(dataASerConvertida.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Método que retorna uma data com o dia anterior a data passada
     *
     * @param data - {@link Date}
     * @return {@link Date}
     */
    public static Date retornaDataUmDiaAnterior(Date data) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - UM_DIA);
        return c.getTime();
    }

    /**
     * Método que retorna uma data subtraindo os dias passado por parâmetro
     *
     * @param data - {@link Date}
     * @param dias dias
     * @return {@link Date}
     */
    public static Date retornarDataAnterior(Date data, int dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - dias);
        return c.getTime();
    }

    /**
     * Método que obtem o ano posterior ao atual
     *
     * @return {@link int} ano posterior
     */
    public static int obterAnoPosterior() {
        return Year.now().getValue() + UM_ANO;
    }

    /**
     * Soma uma quantidade de dias a data
     *
     * @param data Data base para qual a soma será realizada
     * @param soma Soma a ser realizada, para subtração, utilize valores negativos
     * @return {@link Date}
     */
    public static Date somarDataDias(Date data, int soma) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(Calendar.DAY_OF_MONTH, soma);
        return gc.getTime();
    }

    /**
     * Soma ou subtrai uma quantidade de meses a data. Para somar, meses deve ser positivo. Para subtrair, meses deve ser negativo.
     *
     * @param data  Data base para qual a soma será realizada
     * @param meses quantidade de meses a somar ou subtrair a data
     * @return {@link Date} resultante
     */
    public static Date alterarMes(Date data, int meses) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(Calendar.MONTH, meses);
        return gc.getTime();
    }

    /**
     * Retornar a Data Atual, caso seja o mesmo ano ou menor do parametro <code>pAnoReferencia</code> <br>
     * ou dia 01/01/<code>pAnoReferencia</code>, ou seja, primeiro dia do ano.
     *
     * @param pAnoReferencia {@link Integer}
     * @return {@link Date} Data atual ou primerio dia do ano de referencia
     */
    public static Date retornarDataAtualOuPrimeiraDataAno(@NotNull Integer pAnoReferencia) {
        return Optional.of(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .filter(date -> pAnoReferencia.compareTo(Year.now().getValue()) <= VALOR_IGUAL)
                .orElse(Date.from(LocalDate.of(pAnoReferencia, Month.JANUARY, UM_DIA).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * Alterar o valor do campo {@link Calendar#YEAR} da data <code>pDataAlterar</code> com o valor passado no parametro <code>pAnoReferencia</code>
     *
     * @param pDataAlterar   {@link Date} Data para alterar
     * @param pAnoReferencia {@link Integer} Ano referencia
     * @return {@link Date} Data do parametro <code>pDataAlterar</code> com o ano do parametro <code>pAnoReferencia</code>
     */
    public static Date alterarAno(Date pDataAlterar, @NotNull Integer pAnoReferencia) {
        if (pDataAlterar == null) {
            return null;
        }
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(pDataAlterar);
        gc.set(Calendar.YEAR, pAnoReferencia);
        return gc.getTime();
    }

    /**
     * Formata data string.
     *
     * @param data    data a ser formatada
     * @param pattern Padrão a ser usado ex: 'dd/MM/yyyy'
     * @return data no formato.
     */
    public static String formataData(Date data, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(data);
    }

    /**
     * Formata uma data utilizando o formato padrão: 'dd/MM/yyyy'.
     *
     * @param data data a ser formatada
     * @return data no formato.
     */
    public static String formataDataPadrao(Date data) {
        return formataData(data, FORMATO_DATA_PADRAO);
    }

    /**
     * Calcular diferenca entre datas int.
     *
     * @param dataInicial data a ser subtraída
     * @param dataFinal   Data Final para o calcúlo da diferença de dias
     * @return diferença de dias.
     */
    public static int calcularDiferencaEntreDatas(Date dataInicial, Date dataFinal) {
        long diferencaEntreAsDatasEmMilessegundos = retornaDataHoraZerada(dataFinal).getTime() - retornaDataHoraZerada(dataInicial).getTime();
        Long diferencaEntreAsDatasEmDias = diferencaEntreAsDatasEmMilessegundos / DIA_EM_MILESSEGUNDOS;
        return diferencaEntreAsDatasEmDias.intValue();
    }

    /**
     * Obter data atual date.
     *
     * @return {@link Date} Data atual
     */
    public static Date obterDataAtual() {
        return new GregorianCalendar().getTime();
    }

    /**
     * Obtem o semestre corrente
     *
     * @return número do semestre
     */
    public static int obterSemestreAtual() {
        Calendar dataAtual = Calendar.getInstance();
        int mes = dataAtual.get(Calendar.MONTH);

        if (mes < Calendar.JULY) {
            return 1;
        }
        return 2;
    }

    /**
     * Obter o ano {@link Calendar#YEAR} da data <code>pDataAlterar</code>
     *
     * @param pData {@link Date} Data que deseja obter o ano
     * @return {@link Date} Ano da data passada como parâmetro
     */
    public static int obterAnoDaData(Date pData) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(pData);
        return gc.get(Calendar.YEAR);
    }

    /**
     * Uni a Data e Hora em apenas um campo Date
     *
     * @param data, hora
     * @return {@link Date}
     */
    public static Date obterDataUsandoHoraEDataSeparada(Date data, Date hora) {
        if (Objects.nonNull(data) && Objects.nonNull(hora)) {
            try {
                String horaAsStr = formataData(hora, "HH:mm:ss.SSS");
                String dataAsStr = formataData(data, "dd/MM/yyyy");
                return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS").parse(dataAsStr + " " + horaAsStr);
            } catch (ParseException e) {
                throw new ServiceException("Não foi possível fazer parse da data e hora informadas", e);
            }
        } else {
            return null;
        }
    }

    /**
     * Verifica se a nova data é menor ou igual a X dias uteis da data antiga
     *
     * @param dataAntiga, novaData, dias
     * @return boolean
     */
    public static boolean verificarNovaDataMenorXDiasUteisDataAntiga(Date dataAntiga, Date novaData, int dias) {
        Date dataRetornada = retornarDataAnteriorUtil(novaData, dias);
        if (dataRetornada.compareTo(dataAntiga) <= 0) {
            return true;
        }

        return false;
    }

    /**
     * Método que retorna uma data subtraindo os dias passado por parâmetro
     * considerando que o dia resultado é útil
     *
     * @param data - {@link Date}
     * @param dias dias
     * @return {@link Date}
     */
    public static Date retornarDataAnteriorUtil(Date data, int dias) {
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - dias);

        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 2);
        }

        return c.getTime();
    }

}
