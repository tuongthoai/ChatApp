package com.hcmus.chatserver.repository.helpers;

import com.hcmus.chatserver.entities.spam.SpamReport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpamRowMapper implements RowMapper<SpamReport> {
    @Override
    public SpamReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        SpamReport spamReport = new SpamReport();
        spamReport.setReportId(rs.getInt("report_id"));
        spamReport.setSenderId(rs.getInt("usersent"));
        spamReport.setSenderName(rs.getString("sender"));
        spamReport.setReportedUserId(rs.getInt("userisreported"));
        spamReport.setReportedUsername(rs.getString("reported_user"));
        spamReport.setCreatedTime(rs.getLong("createdtime"));
        spamReport.setContent(rs.getString("content"));
        spamReport.setStatus(rs.getString("status"));
        return spamReport;
    }
}
