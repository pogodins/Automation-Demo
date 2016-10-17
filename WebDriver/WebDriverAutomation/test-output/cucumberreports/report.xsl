<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
	<html>
		<head>
			<link rel="stylesheet" type="text/css" href="styles.css"/>
		</head>
		<body>
			<h2>Feature: <xsl:value-of select="feature/name"/></h2>
			<table>
				<xsl:for-each select="feature/scenarios/scenario">
				<tr>
					<td class = "scenario">
						<h3>Scenario: <xsl:value-of select="name"/></h3>
						<table class = "scenario">
							<xsl:for-each select="steps/step">
							<xsl:element name="tr">
								<xsl:attribute name="id">
									<xsl:value-of select="result/status"/>
								</xsl:attribute>
								
								<td><xsl:value-of select="keyword"/>: <xsl:value-of select="name"/></td>
								<td class = "status">
									<xsl:element name="a">
										<xsl:attribute name="href">
											<xsl:value-of select="result/imageURL"/>
										</xsl:attribute>
										<xsl:value-of select="result/status"/>
									</xsl:element>
								</td>
							</xsl:element>
							</xsl:for-each>
						</table>
					</td>
				</tr>
				</xsl:for-each>
			</table>
		</body>
	</html>
</xsl:template>

</xsl:stylesheet> 