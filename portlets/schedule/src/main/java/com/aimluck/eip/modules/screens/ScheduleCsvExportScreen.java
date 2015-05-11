/*
 * Aipo is a groupware program developed by Aimluck,Inc.
 * Copyright (C) 2004-2015 Aimluck,Inc.
 * http://www.aipo.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.aimluck.eip.modules.screens;

import java.util.ListIterator;

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;

import com.aimluck.eip.cayenne.om.portlet.EipTSchedule;
import com.aimluck.eip.cayenne.om.portlet.VEipTScheduleList;
import com.aimluck.eip.common.ALPermissionException;
import com.aimluck.eip.orm.Database;
import com.aimluck.eip.orm.query.ResultList;
import com.aimluck.eip.orm.query.SelectQuery;
import com.aimluck.eip.schedule.ScheduleResultData;
import com.aimluck.eip.schedule.ScheduleSearchResultData;
import com.aimluck.eip.util.ALEipUtils;
import com.aimluck.eip.util.ALLocalizationUtils;

/**
 *
 *
 */
public class ScheduleCsvExportScreen extends ALCSVScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(ScheduleCsvExportScreen.class.getName());

  /**
   *
   * @param rundata
   * @return
   */
  @Override
  protected String getContentType(RunData rundata) {
    return "application/octet-stream";
  }

  /**
   * ResultData に値を格納して返します。（一覧データ） <BR>
   *
   * @param obj
   * @return
   */
  protected Object getResultData(VEipTScheduleList record) {
    /* throws ALPageNotFoundException, ALDBErrorException { */

    try {
      ScheduleSearchResultData rd = new ScheduleSearchResultData();
      rd.initField();
      if ("R".equals(record.getStatus())) {
        return null;
      }

      boolean is_member = record.isMember();

      if ("C".equals(record.getPublicFlag())
        && (userid != record.getOwnerId().intValue())
        && !is_member) {
        rd.setName(ALLocalizationUtils.getl10n("SCHEDULE_CLOSE_PUBLIC_WORD"));
        // 仮スケジュールかどうか
        rd.setTmpreserve(false);
      } else {
        rd.setName(record.getName());
        // 仮スケジュールかどうか
        rd.setTmpreserve("T".equals(record.getStatus()));
      }

      // ID
      rd.setScheduleId(record.getScheduleId().intValue());
      // 親スケジュール ID
      rd.setParentId(record.getParentId().intValue());
      // 開始日時
      rd.setStartDate(record.getStartDate());
      // 終了日時
      rd.setEndDate(record.getEndDate());
      // 公開するかどうか
      rd.setPublic("O".equals(record.getPublicFlag()));
      // 非表示にするかどうか
      rd.setHidden("P".equals(record.getPublicFlag()));
      // ダミーか
      rd.setDummy("D".equals(record.getStatus()));
      // ログインユーザかどうか
      // rd.setLoginuser(is_member);
      // オーナーかどうか
      rd.setOwner(record.getOwnerId().intValue() == userid);
      // 共有メンバーかどうか
      rd.setMember(is_member);
      // 繰り返しパターン
      rd.setPattern(record.getRepeatPattern());
      rd.setCreateUser(ALEipUtils.getALEipUser(record.getCreateUserId()));
      rd.setNote(record.getNote());
      rd.setPlace(record.getPlace());
      rd.setDescription(record.getNote());

      if (!rd.getPattern().equals("N") && !rd.getPattern().equals("S")) {
        rd.setRepeat(true);
      }
    } catch (Exception e) {
      logger.error("schedule", e);
      return null;
    }
    return rd;
  }

  // }

  /**
   *
   */
  @Override
  protected String getCSVString(RunData rundata) throws Exception {
    if (ALEipUtils.isAdmin(rundata)) {
      SelectQuery<EipTSchedule> query = Database.query(EipTSchedule.class);

      ResultList<EipTSchedule> list = query.getResultList();
      String LINE_SEPARATOR = System.getProperty("line.separator");
      try {
        StringBuffer sb =
          new StringBuffer("\"日時\",\"タイトル\",\"作成者\",\"操作\",\"接続IP\",\"件名\"");
        ScheduleResultData data;
        for (ListIterator<EipTSchedule> iterator =
          list.listIterator(list.size()); iterator.hasPrevious();) {
          sb.append(LINE_SEPARATOR);
          data = getResultData(iterator.previous());
          sb.append("\"");
          sb.append(data.getEventDate());
          sb.append("\",\"");
          sb.append(data.getUserFullName());
          sb.append("\",\"");
          sb.append(data.getPortletName());
          sb.append("\",\"");
          sb.append(data.getEventName());
          sb.append("\",\"");
          sb.append(data.getIpAddr());
          sb.append("\",\"");
          sb.append(data.getNote());
          sb.append("\"");
        }
        return sb.toString();
      } catch (Exception e) {
        logger.error("ScheduleCsvExportScreen.getCSVString", e);
        return null;
      }
    } else {
      throw new ALPermissionException();
    }
  }

  @Override
  protected String getFileName() {
    return "schedule.csv";
  }

}