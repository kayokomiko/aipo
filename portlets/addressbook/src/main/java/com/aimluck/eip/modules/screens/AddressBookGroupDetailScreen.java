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

import org.apache.jetspeed.services.logging.JetspeedLogFactoryService;
import org.apache.jetspeed.services.logging.JetspeedLogger;
import org.apache.turbine.util.RunData;
import org.apache.velocity.context.Context;

import com.aimluck.eip.addressbook.AddressBookGroupSelectData;
import com.aimluck.eip.addressbook.util.AddressBookUtils;
import com.aimluck.eip.util.ALEipUtils;

/**
 * AddressBookGroupDetailScreen
 * 
 */
public class AddressBookGroupDetailScreen extends ALVelocityScreen {

  /** logger */
  private static final JetspeedLogger logger = JetspeedLogFactoryService
    .getLogger(AddressBookGroupDetailScreen.class.getName());

  /**
   * アドレス帳グループの詳細画面を処理するクラスです。
   */
  @Override
  protected void doOutput(RunData rundata, Context context) throws Exception {
    try {
      AddressBookGroupSelectData detailData = new AddressBookGroupSelectData();
      detailData.initField();
      detailData.doViewDetail(this, rundata, context);

      String layout_template =
        "portlets/html/ajax-addressbook-group-detail.vm";
      setTemplate(rundata, context, layout_template);
    } catch (Exception ex) {
      logger.error("AddressBookGroupDetailScreen.doOutput", ex);
      ALEipUtils.redirectDBError(rundata);
    }
  }

  /**
   * @return
   */
  @Override
  protected String getPortletName() {
    return AddressBookUtils.ADDRESSBOOK_PORTLET_NAME;
  }

}
