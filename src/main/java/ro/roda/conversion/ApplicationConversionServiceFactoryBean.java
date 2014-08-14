package ro.roda.conversion;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import ro.roda.domain.*;
import ro.roda.service.*;

@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	public Converter<Users, String> getUsersToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Users, java.lang.String>() {
			public String convert(Users users) {
				return new StringBuilder().append(users.getUsername()).toString();
			}
		};
	}

	public Converter<Catalog, String> getCatalogToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Catalog, java.lang.String>() {
			public String convert(Catalog catalog) {
				return new StringBuilder().append(catalog.getName()).append(" - ").append(catalog.getDescription())
						.toString();
			}
		};
	}

	@Autowired
	AclClassService aclClassService;

	@Autowired
	AclEntryService aclEntryService;

	@Autowired
	AclObjectIdentityService aclObjectIdentityService;

	@Autowired
	AclSidService aclSidService;

	@Autowired
	AddressService addressService;

	@Autowired
	AuthoritiesService authoritiesService;

	@Autowired
	CatalogService catalogService;

	@Autowired
	CatalogStudyService catalogStudyService;

	@Autowired
	CityService cityService;

	@Autowired
	CmsFileService cmsFileService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsLayoutService cmsLayoutService;

	@Autowired
	CmsLayoutGroupService cmsLayoutGroupService;

	@Autowired
	CmsPageService cmsPageService;

	@Autowired
	CmsPageContentService cmsPageContentService;

	@Autowired
	CmsPageTypeService cmsPageTypeService;

	@Autowired
	CmsSnippetService cmsSnippetService;

	@Autowired
	CmsSnippetGroupService cmsSnippetGroupService;

	@Autowired
	CollectionModelTypeService collectionModelTypeService;

	@Autowired
	ConceptService conceptService;

	@Autowired
	CountryService countryService;

	@Autowired
	DataSourceTypeService dataSourceTypeService;

	@Autowired
	EmailService emailService;

	@Autowired
	FileService fileService;

	@Autowired
	FormService formService;

	@Autowired
	FormEditedNumberVarService formEditedNumberVarService;

	@Autowired
	FormEditedTextVarService formEditedTextVarService;

	@Autowired
	FormSelectionVarService formSelectionVarService;

	@Autowired
	InstanceService instanceService;

	@Autowired
	InstanceDescrService instanceDescrService;

	@Autowired
	InstanceFormService instanceFormService;

	@Autowired
	InstanceOrgService instanceOrgService;

	@Autowired
	InstanceOrgAssocService instanceOrgAssocService;

	@Autowired
	InstancePersonService instancePersonService;

	@Autowired
	InstancePersonAssocService instancePersonAssocService;

	@Autowired
	InstanceRightService instanceRightService;

	@Autowired
	InstanceRightTargetGroupService instanceRightTargetGroupService;

	@Autowired
	InstanceRightValueService instanceRightValueService;

	@Autowired
	InternetService internetService;

	@Autowired
	ItemService itemService;

	@Autowired
	KeywordService keywordService;

	@Autowired
	LangService langService;

	@Autowired
	NewsService newsService;

	@Autowired
	OrgService orgService;

	@Autowired
	OrgAddressService orgAddressService;

	@Autowired
	OrgEmailService orgEmailService;

	@Autowired
	OrgInternetService orgInternetService;

	@Autowired
	OrgPhoneService orgPhoneService;

	@Autowired
	OrgPrefixService orgPrefixService;

	@Autowired
	OrgRelationTypeService orgRelationTypeService;

	@Autowired
	OrgRelationsService orgRelationsService;

	@Autowired
	OrgSufixService orgSufixService;

	@Autowired
	OtherStatisticService otherStatisticService;

	@Autowired
	PersonService personService;

	@Autowired
	PersonAddressService personAddressService;

	@Autowired
	PersonEmailService personEmailService;

	@Autowired
	PersonInternetService personInternetService;

	@Autowired
	PersonLinksService personLinksService;

	@Autowired
	PersonOrgService personOrgService;

	@Autowired
	PersonPhoneService personPhoneService;

	@Autowired
	PersonRoleService personRoleService;

	@Autowired
	PhoneService phoneService;

	@Autowired
	PrefixService prefixService;

	@Autowired
	RegionService regionService;

	@Autowired
	RegiontypeService regiontypeService;

	@Autowired
	SamplingProcedureService samplingProcedureService;

	@Autowired
	ScaleService scaleService;

	@Autowired
	SelectionVariableService selectionVariableService;

	@Autowired
	SelectionVariableItemService selectionVariableItemService;

	@Autowired
	SeriesService seriesService;

	@Autowired
	SeriesDescrService seriesDescrService;

	@Autowired
	SettingService settingService;

	@Autowired
	SkipService skipService;

	@Autowired
	SourceService sourceService;

	@Autowired
	StudyService studyService;

	@Autowired
	StudyDescrService studyDescrService;

	@Autowired
	StudyKeywordService studyKeywordService;

	@Autowired
	StudyOrgService studyOrgService;

	@Autowired
	StudyOrgAssocService studyOrgAssocService;

	@Autowired
	StudyPersonService studyPersonService;

	@Autowired
	StudyPersonAssocService studyPersonAssocService;

	@Autowired
	SuffixService suffixService;

	@Autowired
	TargetGroupService targetGroupService;

	@Autowired
	TimeMethService timeMethTypeService;

	@Autowired
	TopicService topicService;

	@Autowired
	TranslatedTopicService translatedTopicService;

	@Autowired
	UnitAnalysisService unitAnalysisService;

	@Autowired
	UserAuthLogService userAuthLogService;

	@Autowired
	UserMessageService userMessageService;

	@Autowired
	UserSettingService userSettingService;

	@Autowired
	UserSettingGroupService userSettingGroupService;

	@Autowired
	UserSettingValueService userSettingValueService;

	@Autowired
	UsersService usersService;

	@Autowired
	ValueService valueService;

	@Autowired
	VargroupService vargroupService;

	@Autowired
	VariableService variableService;

	public Converter<AclClass, String> getAclClassToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AclClass, java.lang.String>() {
			public String convert(AclClass aclClass) {
				return new StringBuilder().append(aclClass.getClass1()).toString();
			}
		};
	}

	public Converter<Long, AclClass> getIdToAclClassConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.AclClass>() {
			public ro.roda.domain.AclClass convert(java.lang.Long id) {
				return aclClassService.findAclClass(id);
			}
		};
	}

	public Converter<String, AclClass> getStringToAclClassConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.AclClass>() {
			public ro.roda.domain.AclClass convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), AclClass.class);
			}
		};
	}

	public Converter<AclEntry, String> getAclEntryToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AclEntry, java.lang.String>() {
			public String convert(AclEntry aclEntry) {
				return new StringBuilder().append(aclEntry.getMask()).append(' ').append(aclEntry.getAceOrder())
						.toString();
			}
		};
	}

	public Converter<Long, AclEntry> getIdToAclEntryConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.AclEntry>() {
			public ro.roda.domain.AclEntry convert(java.lang.Long id) {
				return aclEntryService.findAclEntry(id);
			}
		};
	}

	public Converter<String, AclEntry> getStringToAclEntryConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.AclEntry>() {
			public ro.roda.domain.AclEntry convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), AclEntry.class);
			}
		};
	}

	public Converter<AclObjectIdentity, String> getAclObjectIdentityToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AclObjectIdentity, java.lang.String>() {
			public String convert(AclObjectIdentity aclObjectIdentity) {
				return new StringBuilder().append(aclObjectIdentity.getObjectIdIdentity()).toString();
			}
		};
	}

	public Converter<Long, AclObjectIdentity> getIdToAclObjectIdentityConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.AclObjectIdentity>() {
			public ro.roda.domain.AclObjectIdentity convert(java.lang.Long id) {
				return aclObjectIdentityService.findAclObjectIdentity(id);
			}
		};
	}

	public Converter<String, AclObjectIdentity> getStringToAclObjectIdentityConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.AclObjectIdentity>() {
			public ro.roda.domain.AclObjectIdentity convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), AclObjectIdentity.class);
			}
		};
	}

	public Converter<AclSid, String> getAclSidToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AclSid, java.lang.String>() {
			public String convert(AclSid aclSid) {
				return new StringBuilder().append(aclSid.getSid()).toString();
			}
		};
	}

	public Converter<Long, AclSid> getIdToAclSidConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.AclSid>() {
			public ro.roda.domain.AclSid convert(java.lang.Long id) {
				return aclSidService.findAclSid(id);
			}
		};
	}

	public Converter<String, AclSid> getStringToAclSidConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.AclSid>() {
			public ro.roda.domain.AclSid convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), AclSid.class);
			}
		};
	}

	public Converter<Address, String> getAddressToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Address, java.lang.String>() {
			public String convert(Address address) {
				return new StringBuilder().append(address.getAddress1()).append(' ').append(address.getAddress2())
						.append(' ').append(address.getSubdivName()).append(' ').append(address.getSubdivCode())
						.toString();
			}
		};
	}

	public Converter<Integer, Address> getIdToAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Address>() {
			public ro.roda.domain.Address convert(java.lang.Integer id) {
				return addressService.findAddress(id);
			}
		};
	}

	public Converter<String, Address> getStringToAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Address>() {
			public ro.roda.domain.Address convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Address.class);
			}
		};
	}

	public Converter<Authorities, String> getAuthoritiesToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Authorities, java.lang.String>() {
			public String convert(Authorities authorities) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<AuthoritiesPK, Authorities> getIdToAuthoritiesConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AuthoritiesPK, ro.roda.domain.Authorities>() {
			public ro.roda.domain.Authorities convert(ro.roda.domain.AuthoritiesPK id) {
				return authoritiesService.findAuthorities(id);
			}
		};
	}

	public Converter<String, Authorities> getStringToAuthoritiesConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Authorities>() {
			public ro.roda.domain.Authorities convert(String id) {
				return getObject().convert(getObject().convert(id, AuthoritiesPK.class), Authorities.class);
			}
		};
	}

	public Converter<Integer, Catalog> getIdToCatalogConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Catalog>() {
			public ro.roda.domain.Catalog convert(java.lang.Integer id) {
				return catalogService.findCatalog(id);
			}
		};
	}

	public Converter<String, Catalog> getStringToCatalogConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Catalog>() {
			public ro.roda.domain.Catalog convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Catalog.class);
			}
		};
	}

	public Converter<CatalogStudy, String> getCatalogStudyToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CatalogStudy, java.lang.String>() {
			public String convert(CatalogStudy catalogStudy) {
				return new StringBuilder().append(catalogStudy.getAdded()).toString();
			}
		};
	}

	public Converter<CatalogStudyPK, CatalogStudy> getIdToCatalogStudyConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CatalogStudyPK, ro.roda.domain.CatalogStudy>() {
			public ro.roda.domain.CatalogStudy convert(ro.roda.domain.CatalogStudyPK id) {
				return catalogStudyService.findCatalogStudy(id);
			}
		};
	}

	public Converter<String, CatalogStudy> getStringToCatalogStudyConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CatalogStudy>() {
			public ro.roda.domain.CatalogStudy convert(String id) {
				return getObject().convert(getObject().convert(id, CatalogStudyPK.class), CatalogStudy.class);
			}
		};
	}

	public Converter<City, String> getCityToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.City, java.lang.String>() {
			public String convert(City city) {
				return new StringBuilder().append(city.getName()).append(' ').append(city.getCityCode()).append(' ')
						.append(city.getCityCodeName()).append(' ').append(city.getCityCodeSup()).toString();
			}
		};
	}

	public Converter<Integer, City> getIdToCityConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.City>() {
			public ro.roda.domain.City convert(java.lang.Integer id) {
				return cityService.findCity(id);
			}
		};
	}

	public Converter<String, City> getStringToCityConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.City>() {
			public ro.roda.domain.City convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), City.class);
			}
		};
	}

	public Converter<CmsFile, String> getCmsFileToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsFile, java.lang.String>() {
			public String convert(CmsFile cmsFile) {
				return new StringBuilder().append(cmsFile.getFilename()).append(' ').append(cmsFile.getLabel())
						.append(' ').append(cmsFile.getFilesize()).toString();
			}
		};
	}

	public Converter<Integer, CmsFile> getIdToCmsFileConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsFile>() {
			public ro.roda.domain.CmsFile convert(java.lang.Integer id) {
				return cmsFileService.findCmsFile(id);
			}
		};
	}

	public Converter<String, CmsFile> getStringToCmsFileConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsFile>() {
			public ro.roda.domain.CmsFile convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsFile.class);
			}
		};
	}

	public Converter<CmsFolder, String> getCmsFolderToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsFolder, java.lang.String>() {
			public String convert(CmsFolder cmsFolder) {
				return new StringBuilder().append(cmsFolder.getName()).append(' ').append(cmsFolder.getDescription())
						.toString();
			}
		};
	}

	public Converter<Integer, CmsFolder> getIdToCmsFolderConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsFolder>() {
			public ro.roda.domain.CmsFolder convert(java.lang.Integer id) {
				return cmsFolderService.findCmsFolder(id);
			}
		};
	}

	public Converter<String, CmsFolder> getStringToCmsFolderConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsFolder>() {
			public ro.roda.domain.CmsFolder convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsFolder.class);
			}
		};
	}

	public Converter<CmsLayout, String> getCmsLayoutToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsLayout, java.lang.String>() {
			public String convert(CmsLayout cmsLayout) {
				return new StringBuilder().append(cmsLayout.getName()).append(' ').append(cmsLayout.getLayoutContent())
						.toString();
			}
		};
	}

	public Converter<Integer, CmsLayout> getIdToCmsLayoutConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsLayout>() {
			public ro.roda.domain.CmsLayout convert(java.lang.Integer id) {
				return cmsLayoutService.findCmsLayout(id);
			}
		};
	}

	public Converter<String, CmsLayout> getStringToCmsLayoutConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsLayout>() {
			public ro.roda.domain.CmsLayout convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsLayout.class);
			}
		};
	}

	public Converter<CmsLayoutGroup, String> getCmsLayoutGroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsLayoutGroup, java.lang.String>() {
			public String convert(CmsLayoutGroup cmsLayoutGroup) {
				return new StringBuilder().append(cmsLayoutGroup.getName()).append(' ')
						.append(cmsLayoutGroup.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, CmsLayoutGroup> getIdToCmsLayoutGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsLayoutGroup>() {
			public ro.roda.domain.CmsLayoutGroup convert(java.lang.Integer id) {
				return cmsLayoutGroupService.findCmsLayoutGroup(id);
			}
		};
	}

	public Converter<String, CmsLayoutGroup> getStringToCmsLayoutGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsLayoutGroup>() {
			public ro.roda.domain.CmsLayoutGroup convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsLayoutGroup.class);
			}
		};
	}

	public Converter<CmsPage, String> getCmsPageToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsPage, java.lang.String>() {
			public String convert(CmsPage cmsPage) {
				return new StringBuilder().append(cmsPage.getName()).append(' ').append(cmsPage.getUrl()).toString();
			}
		};
	}

	public Converter<Integer, CmsPage> getIdToCmsPageConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsPage>() {
			public ro.roda.domain.CmsPage convert(java.lang.Integer id) {
				return cmsPageService.findCmsPage(id);
			}
		};
	}

	public Converter<String, CmsPage> getStringToCmsPageConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsPage>() {
			public ro.roda.domain.CmsPage convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsPage.class);
			}
		};
	}

	public Converter<CmsPageContent, String> getCmsPageContentToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsPageContent, java.lang.String>() {
			public String convert(CmsPageContent cmsPageContent) {
				return new StringBuilder().append(cmsPageContent.getName()).append(' ')
						.append(cmsPageContent.getContentTitle()).append(' ').append(cmsPageContent.getContentText())
						.append(' ').append(cmsPageContent.getOrderInPage()).toString();
			}
		};
	}

	public Converter<Integer, CmsPageContent> getIdToCmsPageContentConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsPageContent>() {
			public ro.roda.domain.CmsPageContent convert(java.lang.Integer id) {
				return cmsPageContentService.findCmsPageContent(id);
			}
		};
	}

	public Converter<String, CmsPageContent> getStringToCmsPageContentConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsPageContent>() {
			public ro.roda.domain.CmsPageContent convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsPageContent.class);
			}
		};
	}

	public Converter<CmsPageType, String> getCmsPageTypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsPageType, java.lang.String>() {
			public String convert(CmsPageType cmsPageType) {
				return new StringBuilder().append(cmsPageType.getName()).append(' ')
						.append(cmsPageType.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, CmsPageType> getIdToCmsPageTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsPageType>() {
			public ro.roda.domain.CmsPageType convert(java.lang.Integer id) {
				return cmsPageTypeService.findCmsPageType(id);
			}
		};
	}

	public Converter<String, CmsPageType> getStringToCmsPageTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsPageType>() {
			public ro.roda.domain.CmsPageType convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsPageType.class);
			}
		};
	}

	public Converter<CmsSnippet, String> getCmsSnippetToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsSnippet, java.lang.String>() {
			public String convert(CmsSnippet cmsSnippet) {
				return new StringBuilder().append(cmsSnippet.getName()).append(' ')
						.append(cmsSnippet.getSnippetContent()).toString();
			}
		};
	}

	public Converter<Integer, CmsSnippet> getIdToCmsSnippetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsSnippet>() {
			public ro.roda.domain.CmsSnippet convert(java.lang.Integer id) {
				return cmsSnippetService.findCmsSnippet(id);
			}
		};
	}

	public Converter<String, CmsSnippet> getStringToCmsSnippetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsSnippet>() {
			public ro.roda.domain.CmsSnippet convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsSnippet.class);
			}
		};
	}

	public Converter<CmsSnippetGroup, String> getCmsSnippetGroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CmsSnippetGroup, java.lang.String>() {
			public String convert(CmsSnippetGroup cmsSnippetGroup) {
				return new StringBuilder().append(cmsSnippetGroup.getName()).append(' ')
						.append(cmsSnippetGroup.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, CmsSnippetGroup> getIdToCmsSnippetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CmsSnippetGroup>() {
			public ro.roda.domain.CmsSnippetGroup convert(java.lang.Integer id) {
				return cmsSnippetGroupService.findCmsSnippetGroup(id);
			}
		};
	}

	public Converter<String, CmsSnippetGroup> getStringToCmsSnippetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CmsSnippetGroup>() {
			public ro.roda.domain.CmsSnippetGroup convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CmsSnippetGroup.class);
			}
		};
	}

	public Converter<CollectionModelType, String> getCollectionModelTypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CollectionModelType, java.lang.String>() {
			public String convert(CollectionModelType collectionModelType) {
				return new StringBuilder().append(collectionModelType.getName()).append(' ')
						.append(collectionModelType.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, CollectionModelType> getIdToCollectionModelTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.CollectionModelType>() {
			public ro.roda.domain.CollectionModelType convert(java.lang.Integer id) {
				return collectionModelTypeService.findCollectionModelType(id);
			}
		};
	}

	public Converter<String, CollectionModelType> getStringToCollectionModelTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CollectionModelType>() {
			public ro.roda.domain.CollectionModelType convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), CollectionModelType.class);
			}
		};
	}

	public Converter<Concept, String> getConceptToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Concept, java.lang.String>() {
			public String convert(Concept concept) {
				return new StringBuilder().append(concept.getName()).append(' ').append(concept.getDescription())
						.toString();
			}
		};
	}

	public Converter<Long, Concept> getIdToConceptConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Concept>() {
			public ro.roda.domain.Concept convert(java.lang.Long id) {
				return conceptService.findConcept(id);
			}
		};
	}

	public Converter<String, Concept> getStringToConceptConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Concept>() {
			public ro.roda.domain.Concept convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Concept.class);
			}
		};
	}

	public Converter<Country, String> getCountryToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Country, java.lang.String>() {
			public String convert(Country country) {
				return new StringBuilder().append(country.getNameRo()).append(' ').append(country.getNameSelf())
						.append(' ').append(country.getNameEn()).append(' ').append(country.getIso3166()).toString();
			}
		};
	}

	public Converter<Integer, Country> getIdToCountryConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Country>() {
			public ro.roda.domain.Country convert(java.lang.Integer id) {
				return countryService.findCountry(id);
			}
		};
	}

	public Converter<String, Country> getStringToCountryConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Country>() {
			public ro.roda.domain.Country convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Country.class);
			}
		};
	}

	public Converter<DataSourceType, String> getDataSourceTypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.DataSourceType, java.lang.String>() {
			public String convert(DataSourceType dataSourceType) {
				return new StringBuilder().append(dataSourceType.getName()).toString();
			}
		};
	}

	public Converter<Integer, DataSourceType> getIdToDataSourceTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.DataSourceType>() {
			public ro.roda.domain.DataSourceType convert(java.lang.Integer id) {
				return dataSourceTypeService.findDataSourceType(id);
			}
		};
	}

	public Converter<String, DataSourceType> getStringToDataSourceTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.DataSourceType>() {
			public ro.roda.domain.DataSourceType convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), DataSourceType.class);
			}
		};
	}

	public Converter<Email, String> getEmailToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Email, java.lang.String>() {
			public String convert(Email email) {
				return new StringBuilder().append(email.getEmail()).toString();
			}
		};
	}

	public Converter<Integer, Email> getIdToEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Email>() {
			public ro.roda.domain.Email convert(java.lang.Integer id) {
				return emailService.findEmail(id);
			}
		};
	}

	public Converter<String, Email> getStringToEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Email>() {
			public ro.roda.domain.Email convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Email.class);
			}
		};
	}

	public Converter<File, String> getFileToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.File, java.lang.String>() {
			public String convert(File file) {
				return new StringBuilder().append(file.getTitle()).append(' ').append(file.getDescription())
						.append(' ').append(file.getName()).append(' ').append(file.getSize()).toString();
			}
		};
	}

	public Converter<Integer, File> getIdToFileConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.File>() {
			public ro.roda.domain.File convert(java.lang.Integer id) {
				return fileService.findFile(id);
			}
		};
	}

	public Converter<String, File> getStringToFileConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.File>() {
			public ro.roda.domain.File convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), File.class);
			}
		};
	}

	public Converter<Form, String> getFormToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Form, java.lang.String>() {
			public String convert(Form form) {
				return new StringBuilder().append(form.getOperatorNotes()).append(' ').append(form.getFormFilledAt())
						.toString();
			}
		};
	}

	public Converter<Long, Form> getIdToFormConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Form>() {
			public ro.roda.domain.Form convert(java.lang.Long id) {
				return formService.findForm(id);
			}
		};
	}

	public Converter<String, Form> getStringToFormConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Form>() {
			public ro.roda.domain.Form convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Form.class);
			}
		};
	}

	public Converter<FormEditedNumberVar, String> getFormEditedNumberVarToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedNumberVar, java.lang.String>() {
			public String convert(FormEditedNumberVar formEditedNumberVar) {
				return new StringBuilder().append(formEditedNumberVar.getValue()).toString();
			}
		};
	}

	public Converter<FormEditedNumberVarPK, FormEditedNumberVar> getIdToFormEditedNumberVarConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedNumberVarPK, ro.roda.domain.FormEditedNumberVar>() {
			public ro.roda.domain.FormEditedNumberVar convert(ro.roda.domain.FormEditedNumberVarPK id) {
				return formEditedNumberVarService.findFormEditedNumberVar(id);
			}
		};
	}

	public Converter<String, FormEditedNumberVar> getStringToFormEditedNumberVarConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormEditedNumberVar>() {
			public ro.roda.domain.FormEditedNumberVar convert(String id) {
				return getObject().convert(getObject().convert(id, FormEditedNumberVarPK.class),
						FormEditedNumberVar.class);
			}
		};
	}

	public Converter<FormEditedTextVar, String> getFormEditedTextVarToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedTextVar, java.lang.String>() {
			public String convert(FormEditedTextVar formEditedTextVar) {
				return new StringBuilder().append(formEditedTextVar.getText()).toString();
			}
		};
	}

	public Converter<FormEditedTextVarPK, FormEditedTextVar> getIdToFormEditedTextVarConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedTextVarPK, ro.roda.domain.FormEditedTextVar>() {
			public ro.roda.domain.FormEditedTextVar convert(ro.roda.domain.FormEditedTextVarPK id) {
				return formEditedTextVarService.findFormEditedTextVar(id);
			}
		};
	}

	public Converter<String, FormEditedTextVar> getStringToFormEditedTextVarConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormEditedTextVar>() {
			public ro.roda.domain.FormEditedTextVar convert(String id) {
				return getObject().convert(getObject().convert(id, FormEditedTextVarPK.class), FormEditedTextVar.class);
			}
		};
	}

	public Converter<FormSelectionVar, String> getFormSelectionVarToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormSelectionVar, java.lang.String>() {
			public String convert(FormSelectionVar formSelectionVar) {
				return new StringBuilder().append(formSelectionVar.getOrderOfItemsInResponse()).toString();
			}
		};
	}

	public Converter<FormSelectionVarPK, FormSelectionVar> getIdToFormSelectionVarConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormSelectionVarPK, ro.roda.domain.FormSelectionVar>() {
			public ro.roda.domain.FormSelectionVar convert(ro.roda.domain.FormSelectionVarPK id) {
				return formSelectionVarService.findFormSelectionVar(id);
			}
		};
	}

	public Converter<String, FormSelectionVar> getStringToFormSelectionVarConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormSelectionVar>() {
			public ro.roda.domain.FormSelectionVar convert(String id) {
				return getObject().convert(getObject().convert(id, FormSelectionVarPK.class), FormSelectionVar.class);
			}
		};
	}

	public Converter<Instance, String> getInstanceToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Instance, java.lang.String>() {
			public String convert(Instance instance) {
				return new StringBuilder().append(instance.getAdded()).append(' ')
						.append(instance.getDisseminatorIdentifier()).toString();
			}
		};
	}

	public Converter<Integer, Instance> getIdToInstanceConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Instance>() {
			public ro.roda.domain.Instance convert(java.lang.Integer id) {
				return instanceService.findInstance(id);
			}
		};
	}

	public Converter<String, Instance> getStringToInstanceConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Instance>() {
			public ro.roda.domain.Instance convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Instance.class);
			}
		};
	}

	public Converter<InstanceDescr, String> getInstanceDescrToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceDescr, java.lang.String>() {
			public String convert(InstanceDescr instanceDescr) {
				return new StringBuilder().append(instanceDescr.getAccessConditions()).append(' ')
						.append(instanceDescr.getNotes()).append(' ').append(instanceDescr.getTitle()).toString();
			}
		};
	}

	public Converter<InstanceDescrPK, InstanceDescr> getIdToInstanceDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceDescrPK, ro.roda.domain.InstanceDescr>() {
			public ro.roda.domain.InstanceDescr convert(ro.roda.domain.InstanceDescrPK id) {
				return instanceDescrService.findInstanceDescr(id);
			}
		};
	}

	public Converter<String, InstanceDescr> getStringToInstanceDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceDescr>() {
			public ro.roda.domain.InstanceDescr convert(String id) {
				return getObject().convert(getObject().convert(id, InstanceDescrPK.class), InstanceDescr.class);
			}
		};
	}

	public Converter<InstanceForm, String> getInstanceFormToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceForm, java.lang.String>() {
			public String convert(InstanceForm instanceForm) {
				return new StringBuilder().append(instanceForm.getOrderFormInInstance()).toString();
			}
		};
	}

	public Converter<InstanceFormPK, InstanceForm> getIdToInstanceFormConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceFormPK, ro.roda.domain.InstanceForm>() {
			public ro.roda.domain.InstanceForm convert(ro.roda.domain.InstanceFormPK id) {
				return instanceFormService.findInstanceForm(id);
			}
		};
	}

	public Converter<String, InstanceForm> getStringToInstanceFormConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceForm>() {
			public ro.roda.domain.InstanceForm convert(String id) {
				return getObject().convert(getObject().convert(id, InstanceFormPK.class), InstanceForm.class);
			}
		};
	}

	public Converter<InstanceOrg, String> getInstanceOrgToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceOrg, java.lang.String>() {
			public String convert(InstanceOrg instanceOrg) {
				return new StringBuilder().append(instanceOrg.getAssocDetails()).toString();
			}
		};
	}

	public Converter<InstanceOrgPK, InstanceOrg> getIdToInstanceOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceOrgPK, ro.roda.domain.InstanceOrg>() {
			public ro.roda.domain.InstanceOrg convert(ro.roda.domain.InstanceOrgPK id) {
				return instanceOrgService.findInstanceOrg(id);
			}
		};
	}

	public Converter<String, InstanceOrg> getStringToInstanceOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceOrg>() {
			public ro.roda.domain.InstanceOrg convert(String id) {
				return getObject().convert(getObject().convert(id, InstanceOrgPK.class), InstanceOrg.class);
			}
		};
	}

	public Converter<InstanceOrgAssoc, String> getInstanceOrgAssocToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceOrgAssoc, java.lang.String>() {
			public String convert(InstanceOrgAssoc instanceOrgAssoc) {
				return new StringBuilder().append(instanceOrgAssoc.getAssocName()).append(' ')
						.append(instanceOrgAssoc.getAssocDescription()).toString();
			}
		};
	}

	public Converter<Integer, InstanceOrgAssoc> getIdToInstanceOrgAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.InstanceOrgAssoc>() {
			public ro.roda.domain.InstanceOrgAssoc convert(java.lang.Integer id) {
				return instanceOrgAssocService.findInstanceOrgAssoc(id);
			}
		};
	}

	public Converter<String, InstanceOrgAssoc> getStringToInstanceOrgAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceOrgAssoc>() {
			public ro.roda.domain.InstanceOrgAssoc convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), InstanceOrgAssoc.class);
			}
		};
	}

	public Converter<InstancePerson, String> getInstancePersonToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstancePerson, java.lang.String>() {
			public String convert(InstancePerson instancePerson) {
				return new StringBuilder().append(instancePerson.getAssocDetails()).toString();
			}
		};
	}

	public Converter<InstancePersonPK, InstancePerson> getIdToInstancePersonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstancePersonPK, ro.roda.domain.InstancePerson>() {
			public ro.roda.domain.InstancePerson convert(ro.roda.domain.InstancePersonPK id) {
				return instancePersonService.findInstancePerson(id);
			}
		};
	}

	public Converter<String, InstancePerson> getStringToInstancePersonConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstancePerson>() {
			public ro.roda.domain.InstancePerson convert(String id) {
				return getObject().convert(getObject().convert(id, InstancePersonPK.class), InstancePerson.class);
			}
		};
	}

	public Converter<InstancePersonAssoc, String> getInstancePersonAssocToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstancePersonAssoc, java.lang.String>() {
			public String convert(InstancePersonAssoc instancePersonAssoc) {
				return new StringBuilder().append(instancePersonAssoc.getAssocName()).append(' ')
						.append(instancePersonAssoc.getAssocDescription()).toString();
			}
		};
	}

	public Converter<Integer, InstancePersonAssoc> getIdToInstancePersonAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.InstancePersonAssoc>() {
			public ro.roda.domain.InstancePersonAssoc convert(java.lang.Integer id) {
				return instancePersonAssocService.findInstancePersonAssoc(id);
			}
		};
	}

	public Converter<String, InstancePersonAssoc> getStringToInstancePersonAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstancePersonAssoc>() {
			public ro.roda.domain.InstancePersonAssoc convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), InstancePersonAssoc.class);
			}
		};
	}

	public Converter<InstanceRight, String> getInstanceRightToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceRight, java.lang.String>() {
			public String convert(InstanceRight instanceRight) {
				return new StringBuilder().append(instanceRight.getName()).append(' ')
						.append(instanceRight.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, InstanceRight> getIdToInstanceRightConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.InstanceRight>() {
			public ro.roda.domain.InstanceRight convert(java.lang.Integer id) {
				return instanceRightService.findInstanceRight(id);
			}
		};
	}

	public Converter<String, InstanceRight> getStringToInstanceRightConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceRight>() {
			public ro.roda.domain.InstanceRight convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), InstanceRight.class);
			}
		};
	}

	public Converter<InstanceRightTargetGroup, String> getInstanceRightTargetGroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceRightTargetGroup, java.lang.String>() {
			public String convert(InstanceRightTargetGroup instanceRightTargetGroup) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<InstanceRightTargetGroupPK, InstanceRightTargetGroup> getIdToInstanceRightTargetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceRightTargetGroupPK, ro.roda.domain.InstanceRightTargetGroup>() {
			public ro.roda.domain.InstanceRightTargetGroup convert(ro.roda.domain.InstanceRightTargetGroupPK id) {
				return instanceRightTargetGroupService.findInstanceRightTargetGroup(id);
			}
		};
	}

	public Converter<String, InstanceRightTargetGroup> getStringToInstanceRightTargetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceRightTargetGroup>() {
			public ro.roda.domain.InstanceRightTargetGroup convert(String id) {
				return getObject().convert(getObject().convert(id, InstanceRightTargetGroupPK.class),
						InstanceRightTargetGroup.class);
			}
		};
	}

	public Converter<InstanceRightValue, String> getInstanceRightValueToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceRightValue, java.lang.String>() {
			public String convert(InstanceRightValue instanceRightValue) {
				return new StringBuilder().append(instanceRightValue.getValue()).append(' ')
						.append(instanceRightValue.getDescription()).append(' ').append(instanceRightValue.getFee())
						.append(' ').append(instanceRightValue.getFeeCurrencyAbbr()).toString();
			}
		};
	}

	public Converter<Integer, InstanceRightValue> getIdToInstanceRightValueConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.InstanceRightValue>() {
			public ro.roda.domain.InstanceRightValue convert(java.lang.Integer id) {
				return instanceRightValueService.findInstanceRightValue(id);
			}
		};
	}

	public Converter<String, InstanceRightValue> getStringToInstanceRightValueConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceRightValue>() {
			public ro.roda.domain.InstanceRightValue convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), InstanceRightValue.class);
			}
		};
	}

	public Converter<Internet, String> getInternetToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Internet, java.lang.String>() {
			public String convert(Internet internet) {
				return new StringBuilder().append(internet.getInternetType()).append(' ')
						.append(internet.getInternet()).toString();
			}
		};
	}

	public Converter<Integer, Internet> getIdToInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Internet>() {
			public ro.roda.domain.Internet convert(java.lang.Integer id) {
				return internetService.findInternet(id);
			}
		};
	}

	public Converter<String, Internet> getStringToInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Internet>() {
			public ro.roda.domain.Internet convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Internet.class);
			}
		};
	}

	public Converter<Item, String> getItemToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Item, java.lang.String>() {
			public String convert(Item item) {
				return new StringBuilder().append(item.getName()).toString();
			}
		};
	}

	public Converter<Long, Item> getIdToItemConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Item>() {
			public ro.roda.domain.Item convert(java.lang.Long id) {
				return itemService.findItem(id);
			}
		};
	}

	public Converter<String, Item> getStringToItemConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Item>() {
			public ro.roda.domain.Item convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Item.class);
			}
		};
	}

	public Converter<Keyword, String> getKeywordToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Keyword, java.lang.String>() {
			public String convert(Keyword keyword) {
				return new StringBuilder().append(keyword.getName()).toString();
			}
		};
	}

	public Converter<Integer, Keyword> getIdToKeywordConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Keyword>() {
			public ro.roda.domain.Keyword convert(java.lang.Integer id) {
				return keywordService.findKeyword(id);
			}
		};
	}

	public Converter<String, Keyword> getStringToKeywordConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Keyword>() {
			public ro.roda.domain.Keyword convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Keyword.class);
			}
		};
	}

	public Converter<Lang, String> getLangToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Lang, java.lang.String>() {
			public String convert(Lang lang) {
				return new StringBuilder().append(lang.getIso639()).append(' ').append(lang.getNameSelf()).append(' ')
						.append(lang.getNameRo()).append(' ').append(lang.getNameEn()).toString();
			}
		};
	}

	public Converter<Integer, Lang> getIdToLangConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Lang>() {
			public ro.roda.domain.Lang convert(java.lang.Integer id) {
				return langService.findLang(id);
			}
		};
	}

	public Converter<String, Lang> getStringToLangConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Lang>() {
			public ro.roda.domain.Lang convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Lang.class);
			}
		};
	}

	public Converter<News, String> getNewsToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.News, java.lang.String>() {
			public String convert(News news) {
				return new StringBuilder().append(news.getAdded()).append(' ').append(news.getTitle()).append(' ')
						.append(news.getContent()).toString();
			}
		};
	}

	public Converter<Integer, News> getIdToNewsConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.News>() {
			public ro.roda.domain.News convert(java.lang.Integer id) {
				return newsService.findNews(id);
			}
		};
	}

	public Converter<String, News> getStringToNewsConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.News>() {
			public ro.roda.domain.News convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), News.class);
			}
		};
	}

	public Converter<Org, String> getOrgToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Org, java.lang.String>() {
			public String convert(Org org) {
				return new StringBuilder().append(org.getShortName()).append(' ').append(org.getFullName()).toString();
			}
		};
	}

	public Converter<Integer, Org> getIdToOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Org>() {
			public ro.roda.domain.Org convert(java.lang.Integer id) {
				return orgService.findOrg(id);
			}
		};
	}

	public Converter<String, Org> getStringToOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Org>() {
			public ro.roda.domain.Org convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Org.class);
			}
		};
	}

	public Converter<OrgAddress, String> getOrgAddressToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgAddress, java.lang.String>() {
			public String convert(OrgAddress orgAddress) {
				return new StringBuilder().append(orgAddress.getDateStart()).append(' ')
						.append(orgAddress.getDateEnd()).toString();
			}
		};
	}

	public Converter<OrgAddressPK, OrgAddress> getIdToOrgAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgAddressPK, ro.roda.domain.OrgAddress>() {
			public ro.roda.domain.OrgAddress convert(ro.roda.domain.OrgAddressPK id) {
				return orgAddressService.findOrgAddress(id);
			}
		};
	}

	public Converter<String, OrgAddress> getStringToOrgAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgAddress>() {
			public ro.roda.domain.OrgAddress convert(String id) {
				return getObject().convert(getObject().convert(id, OrgAddressPK.class), OrgAddress.class);
			}
		};
	}

	public Converter<OrgEmail, String> getOrgEmailToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgEmail, java.lang.String>() {
			public String convert(OrgEmail orgEmail) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<OrgEmailPK, OrgEmail> getIdToOrgEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgEmailPK, ro.roda.domain.OrgEmail>() {
			public ro.roda.domain.OrgEmail convert(ro.roda.domain.OrgEmailPK id) {
				return orgEmailService.findOrgEmail(id);
			}
		};
	}

	public Converter<String, OrgEmail> getStringToOrgEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgEmail>() {
			public ro.roda.domain.OrgEmail convert(String id) {
				return getObject().convert(getObject().convert(id, OrgEmailPK.class), OrgEmail.class);
			}
		};
	}

	public Converter<OrgInternet, String> getOrgInternetToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgInternet, java.lang.String>() {
			public String convert(OrgInternet orgInternet) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<OrgInternetPK, OrgInternet> getIdToOrgInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgInternetPK, ro.roda.domain.OrgInternet>() {
			public ro.roda.domain.OrgInternet convert(ro.roda.domain.OrgInternetPK id) {
				return orgInternetService.findOrgInternet(id);
			}
		};
	}

	public Converter<String, OrgInternet> getStringToOrgInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgInternet>() {
			public ro.roda.domain.OrgInternet convert(String id) {
				return getObject().convert(getObject().convert(id, OrgInternetPK.class), OrgInternet.class);
			}
		};
	}

	public Converter<OrgPhone, String> getOrgPhoneToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgPhone, java.lang.String>() {
			public String convert(OrgPhone orgPhone) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<OrgPhonePK, OrgPhone> getIdToOrgPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgPhonePK, ro.roda.domain.OrgPhone>() {
			public ro.roda.domain.OrgPhone convert(ro.roda.domain.OrgPhonePK id) {
				return orgPhoneService.findOrgPhone(id);
			}
		};
	}

	public Converter<String, OrgPhone> getStringToOrgPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgPhone>() {
			public ro.roda.domain.OrgPhone convert(String id) {
				return getObject().convert(getObject().convert(id, OrgPhonePK.class), OrgPhone.class);
			}
		};
	}

	public Converter<OrgPrefix, String> getOrgPrefixToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgPrefix, java.lang.String>() {
			public String convert(OrgPrefix orgPrefix) {
				return new StringBuilder().append(orgPrefix.getName()).append(' ').append(orgPrefix.getDescription())
						.toString();
			}
		};
	}

	public Converter<Integer, OrgPrefix> getIdToOrgPrefixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.OrgPrefix>() {
			public ro.roda.domain.OrgPrefix convert(java.lang.Integer id) {
				return orgPrefixService.findOrgPrefix(id);
			}
		};
	}

	public Converter<String, OrgPrefix> getStringToOrgPrefixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgPrefix>() {
			public ro.roda.domain.OrgPrefix convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), OrgPrefix.class);
			}
		};
	}

	public Converter<OrgRelationType, String> getOrgRelationTypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgRelationType, java.lang.String>() {
			public String convert(OrgRelationType orgRelationType) {
				return new StringBuilder().append(orgRelationType.getName()).toString();
			}
		};
	}

	public Converter<Integer, OrgRelationType> getIdToOrgRelationTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.OrgRelationType>() {
			public ro.roda.domain.OrgRelationType convert(java.lang.Integer id) {
				return orgRelationTypeService.findOrgRelationType(id);
			}
		};
	}

	public Converter<String, OrgRelationType> getStringToOrgRelationTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgRelationType>() {
			public ro.roda.domain.OrgRelationType convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), OrgRelationType.class);
			}
		};
	}

	public Converter<OrgRelations, String> getOrgRelationsToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgRelations, java.lang.String>() {
			public String convert(OrgRelations orgRelations) {
				return new StringBuilder().append(orgRelations.getDateStart()).append(' ')
						.append(orgRelations.getDateEnd()).append(' ').append(orgRelations.getDetails()).toString();
			}
		};
	}

	public Converter<OrgRelationsPK, OrgRelations> getIdToOrgRelationsConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgRelationsPK, ro.roda.domain.OrgRelations>() {
			public ro.roda.domain.OrgRelations convert(ro.roda.domain.OrgRelationsPK id) {
				return orgRelationsService.findOrgRelations(id);
			}
		};
	}

	public Converter<String, OrgRelations> getStringToOrgRelationsConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgRelations>() {
			public ro.roda.domain.OrgRelations convert(String id) {
				return getObject().convert(getObject().convert(id, OrgRelationsPK.class), OrgRelations.class);
			}
		};
	}

	public Converter<OrgSufix, String> getOrgSufixToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgSufix, java.lang.String>() {
			public String convert(OrgSufix orgSufix) {
				return new StringBuilder().append(orgSufix.getName()).append(' ').append(orgSufix.getDescription())
						.toString();
			}
		};
	}

	public Converter<Integer, OrgSufix> getIdToOrgSufixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.OrgSufix>() {
			public ro.roda.domain.OrgSufix convert(java.lang.Integer id) {
				return orgSufixService.findOrgSufix(id);
			}
		};
	}

	public Converter<String, OrgSufix> getStringToOrgSufixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgSufix>() {
			public ro.roda.domain.OrgSufix convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), OrgSufix.class);
			}
		};
	}

	public Converter<OtherStatistic, String> getOtherStatisticToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OtherStatistic, java.lang.String>() {
			public String convert(OtherStatistic otherStatistic) {
				return new StringBuilder().append(otherStatistic.getName()).append(' ')
						.append(otherStatistic.getValue()).append(' ').append(otherStatistic.getDescription())
						.toString();
			}
		};
	}

	public Converter<Long, OtherStatistic> getIdToOtherStatisticConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.OtherStatistic>() {
			public ro.roda.domain.OtherStatistic convert(java.lang.Long id) {
				return otherStatisticService.findOtherStatistic(id);
			}
		};
	}

	public Converter<String, OtherStatistic> getStringToOtherStatisticConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OtherStatistic>() {
			public ro.roda.domain.OtherStatistic convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), OtherStatistic.class);
			}
		};
	}

	public Converter<Person, String> getPersonToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Person, java.lang.String>() {
			public String convert(Person person) {
				return new StringBuilder().append(person.getFname()).append(' ').append(person.getMname()).append(' ')
						.append(person.getLname()).toString();
			}
		};
	}

	public Converter<Integer, Person> getIdToPersonConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Person>() {
			public ro.roda.domain.Person convert(java.lang.Integer id) {
				return personService.findPerson(id);
			}
		};
	}

	public Converter<String, Person> getStringToPersonConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Person>() {
			public ro.roda.domain.Person convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Person.class);
			}
		};
	}

	public Converter<PersonAddress, String> getPersonAddressToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonAddress, java.lang.String>() {
			public String convert(PersonAddress personAddress) {
				return new StringBuilder().append(personAddress.getDateStart()).append(' ')
						.append(personAddress.getDateEnd()).toString();
			}
		};
	}

	public Converter<PersonAddressPK, PersonAddress> getIdToPersonAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonAddressPK, ro.roda.domain.PersonAddress>() {
			public ro.roda.domain.PersonAddress convert(ro.roda.domain.PersonAddressPK id) {
				return personAddressService.findPersonAddress(id);
			}
		};
	}

	public Converter<String, PersonAddress> getStringToPersonAddressConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonAddress>() {
			public ro.roda.domain.PersonAddress convert(String id) {
				return getObject().convert(getObject().convert(id, PersonAddressPK.class), PersonAddress.class);
			}
		};
	}

	public Converter<PersonEmail, String> getPersonEmailToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonEmail, java.lang.String>() {
			public String convert(PersonEmail personEmail) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<PersonEmailPK, PersonEmail> getIdToPersonEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonEmailPK, ro.roda.domain.PersonEmail>() {
			public ro.roda.domain.PersonEmail convert(ro.roda.domain.PersonEmailPK id) {
				return personEmailService.findPersonEmail(id);
			}
		};
	}

	public Converter<String, PersonEmail> getStringToPersonEmailConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonEmail>() {
			public ro.roda.domain.PersonEmail convert(String id) {
				return getObject().convert(getObject().convert(id, PersonEmailPK.class), PersonEmail.class);
			}
		};
	}

	public Converter<PersonInternet, String> getPersonInternetToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonInternet, java.lang.String>() {
			public String convert(PersonInternet personInternet) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<PersonInternetPK, PersonInternet> getIdToPersonInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonInternetPK, ro.roda.domain.PersonInternet>() {
			public ro.roda.domain.PersonInternet convert(ro.roda.domain.PersonInternetPK id) {
				return personInternetService.findPersonInternet(id);
			}
		};
	}

	public Converter<String, PersonInternet> getStringToPersonInternetConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonInternet>() {
			public ro.roda.domain.PersonInternet convert(String id) {
				return getObject().convert(getObject().convert(id, PersonInternetPK.class), PersonInternet.class);
			}
		};
	}

	public Converter<PersonLinks, String> getPersonLinksToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonLinks, java.lang.String>() {
			public String convert(PersonLinks personLinks) {
				return new StringBuilder().append(personLinks.getSimscore()).append(' ')
						.append(personLinks.getNamescore()).append(' ').append(personLinks.getEmailscore()).append(' ')
						.append(personLinks.getStatus()).toString();
			}
		};
	}

	public Converter<Integer, PersonLinks> getIdToPersonLinksConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.PersonLinks>() {
			public ro.roda.domain.PersonLinks convert(java.lang.Integer id) {
				return personLinksService.findPersonLinks(id);
			}
		};
	}

	public Converter<String, PersonLinks> getStringToPersonLinksConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonLinks>() {
			public ro.roda.domain.PersonLinks convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), PersonLinks.class);
			}
		};
	}

	public Converter<PersonOrg, String> getPersonOrgToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonOrg, java.lang.String>() {
			public String convert(PersonOrg personOrg) {
				return new StringBuilder().append(personOrg.getDateStart()).append(' ').append(personOrg.getDateEnd())
						.toString();
			}
		};
	}

	public Converter<PersonOrgPK, PersonOrg> getIdToPersonOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonOrgPK, ro.roda.domain.PersonOrg>() {
			public ro.roda.domain.PersonOrg convert(ro.roda.domain.PersonOrgPK id) {
				return personOrgService.findPersonOrg(id);
			}
		};
	}

	public Converter<String, PersonOrg> getStringToPersonOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonOrg>() {
			public ro.roda.domain.PersonOrg convert(String id) {
				return getObject().convert(getObject().convert(id, PersonOrgPK.class), PersonOrg.class);
			}
		};
	}

	public Converter<PersonPhone, String> getPersonPhoneToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonPhone, java.lang.String>() {
			public String convert(PersonPhone personPhone) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<PersonPhonePK, PersonPhone> getIdToPersonPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonPhonePK, ro.roda.domain.PersonPhone>() {
			public ro.roda.domain.PersonPhone convert(ro.roda.domain.PersonPhonePK id) {
				return personPhoneService.findPersonPhone(id);
			}
		};
	}

	public Converter<String, PersonPhone> getStringToPersonPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonPhone>() {
			public ro.roda.domain.PersonPhone convert(String id) {
				return getObject().convert(getObject().convert(id, PersonPhonePK.class), PersonPhone.class);
			}
		};
	}

	public Converter<PersonRole, String> getPersonRoleToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonRole, java.lang.String>() {
			public String convert(PersonRole personRole) {
				return new StringBuilder().append(personRole.getName()).toString();
			}
		};
	}

	public Converter<Integer, PersonRole> getIdToPersonRoleConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.PersonRole>() {
			public ro.roda.domain.PersonRole convert(java.lang.Integer id) {
				return personRoleService.findPersonRole(id);
			}
		};
	}

	public Converter<String, PersonRole> getStringToPersonRoleConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonRole>() {
			public ro.roda.domain.PersonRole convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), PersonRole.class);
			}
		};
	}

	public Converter<Phone, String> getPhoneToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Phone, java.lang.String>() {
			public String convert(Phone phone) {
				return new StringBuilder().append(phone.getPhone()).append(' ').append(phone.getPhoneType()).toString();
			}
		};
	}

	public Converter<Integer, Phone> getIdToPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Phone>() {
			public ro.roda.domain.Phone convert(java.lang.Integer id) {
				return phoneService.findPhone(id);
			}
		};
	}

	public Converter<String, Phone> getStringToPhoneConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Phone>() {
			public ro.roda.domain.Phone convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Phone.class);
			}
		};
	}

	public Converter<Prefix, String> getPrefixToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Prefix, java.lang.String>() {
			public String convert(Prefix prefix) {
				return new StringBuilder().append(prefix.getName()).toString();
			}
		};
	}

	public Converter<Integer, Prefix> getIdToPrefixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Prefix>() {
			public ro.roda.domain.Prefix convert(java.lang.Integer id) {
				return prefixService.findPrefix(id);
			}
		};
	}

	public Converter<String, Prefix> getStringToPrefixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Prefix>() {
			public ro.roda.domain.Prefix convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Prefix.class);
			}
		};
	}

	public Converter<Region, String> getRegionToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Region, java.lang.String>() {
			public String convert(Region region) {
				return new StringBuilder().append(region.getName()).append(' ').append(region.getRegionCode())
						.append(' ').append(region.getRegionCodeName()).toString();
			}
		};
	}

	public Converter<Integer, Region> getIdToRegionConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Region>() {
			public ro.roda.domain.Region convert(java.lang.Integer id) {
				return regionService.findRegion(id);
			}
		};
	}

	public Converter<String, Region> getStringToRegionConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Region>() {
			public ro.roda.domain.Region convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Region.class);
			}
		};
	}

	public Converter<Regiontype, String> getRegiontypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Regiontype, java.lang.String>() {
			public String convert(Regiontype regiontype) {
				return new StringBuilder().append(regiontype.getName()).toString();
			}
		};
	}

	public Converter<Integer, Regiontype> getIdToRegiontypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Regiontype>() {
			public ro.roda.domain.Regiontype convert(java.lang.Integer id) {
				return regiontypeService.findRegiontype(id);
			}
		};
	}

	public Converter<String, Regiontype> getStringToRegiontypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Regiontype>() {
			public ro.roda.domain.Regiontype convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Regiontype.class);
			}
		};
	}

	public Converter<SamplingProcedure, String> getSamplingProcedureToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SamplingProcedure, java.lang.String>() {
			public String convert(SamplingProcedure samplingProcedure) {
				return new StringBuilder().append(samplingProcedure.getName()).append(' ')
						.append(samplingProcedure.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, SamplingProcedure> getIdToSamplingProcedureConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.SamplingProcedure>() {
			public ro.roda.domain.SamplingProcedure convert(java.lang.Integer id) {
				return samplingProcedureService.findSamplingProcedure(id);
			}
		};
	}

	public Converter<String, SamplingProcedure> getStringToSamplingProcedureConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SamplingProcedure>() {
			public ro.roda.domain.SamplingProcedure convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), SamplingProcedure.class);
			}
		};
	}

	public Converter<Scale, String> getScaleToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Scale, java.lang.String>() {
			public String convert(Scale scale) {
				return new StringBuilder().append(scale.getUnits()).toString();
			}
		};
	}

	public Converter<Long, Scale> getIdToScaleConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Scale>() {
			public ro.roda.domain.Scale convert(java.lang.Long id) {
				return scaleService.findScale(id);
			}
		};
	}

	public Converter<String, Scale> getStringToScaleConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Scale>() {
			public ro.roda.domain.Scale convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Scale.class);
			}
		};
	}

	public Converter<SelectionVariable, String> getSelectionVariableToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SelectionVariable, java.lang.String>() {
			public String convert(SelectionVariable selectionVariable) {
				return new StringBuilder().append(selectionVariable.getMinCount()).append(' ')
						.append(selectionVariable.getMaxCount()).toString();
			}
		};
	}

	public Converter<Long, SelectionVariable> getIdToSelectionVariableConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.SelectionVariable>() {
			public ro.roda.domain.SelectionVariable convert(java.lang.Long id) {
				return selectionVariableService.findSelectionVariable(id);
			}
		};
	}

	public Converter<String, SelectionVariable> getStringToSelectionVariableConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SelectionVariable>() {
			public ro.roda.domain.SelectionVariable convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), SelectionVariable.class);
			}
		};
	}

	public Converter<SelectionVariableItem, String> getSelectionVariableItemToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SelectionVariableItem, java.lang.String>() {
			public String convert(SelectionVariableItem selectionVariableItem) {
				return new StringBuilder().append(selectionVariableItem.getOrderOfItemInVariable()).append(' ')
						.append(selectionVariableItem.getFrequencyValue()).toString();
			}
		};
	}

	public Converter<SelectionVariableItemPK, SelectionVariableItem> getIdToSelectionVariableItemConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SelectionVariableItemPK, ro.roda.domain.SelectionVariableItem>() {
			public ro.roda.domain.SelectionVariableItem convert(ro.roda.domain.SelectionVariableItemPK id) {
				return selectionVariableItemService.findSelectionVariableItem(id);
			}
		};
	}

	public Converter<String, SelectionVariableItem> getStringToSelectionVariableItemConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SelectionVariableItem>() {
			public ro.roda.domain.SelectionVariableItem convert(String id) {
				return getObject().convert(getObject().convert(id, SelectionVariableItemPK.class),
						SelectionVariableItem.class);
			}
		};
	}

	public Converter<Series, String> getSeriesToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Series, java.lang.String>() {
			public String convert(Series series) {
				return "(no displayable fields)";
			}
		};
	}

	public Converter<Integer, Series> getIdToSeriesConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Series>() {
			public ro.roda.domain.Series convert(java.lang.Integer id) {
				return seriesService.findSeries(id);
			}
		};
	}

	public Converter<String, Series> getStringToSeriesConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Series>() {
			public ro.roda.domain.Series convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Series.class);
			}
		};
	}

	public Converter<SeriesDescr, String> getSeriesDescrToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SeriesDescr, java.lang.String>() {
			public String convert(SeriesDescr seriesDescr) {
				return new StringBuilder().append(seriesDescr.getNotes()).append(' ').append(seriesDescr.getTitle())
						.append(' ').append(seriesDescr.getSubtitle()).append(' ')
						.append(seriesDescr.getAlternativeTitle()).toString();
			}
		};
	}

	public Converter<SeriesDescrPK, SeriesDescr> getIdToSeriesDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SeriesDescrPK, ro.roda.domain.SeriesDescr>() {
			public ro.roda.domain.SeriesDescr convert(ro.roda.domain.SeriesDescrPK id) {
				return seriesDescrService.findSeriesDescr(id);
			}
		};
	}

	public Converter<String, SeriesDescr> getStringToSeriesDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SeriesDescr>() {
			public ro.roda.domain.SeriesDescr convert(String id) {
				return getObject().convert(getObject().convert(id, SeriesDescrPK.class), SeriesDescr.class);
			}
		};
	}

	public Converter<Setting, String> getSettingToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Setting, java.lang.String>() {
			public String convert(Setting setting) {
				return new StringBuilder().append(setting.getName()).append(' ').append(setting.getDescription())
						.append(' ').append(setting.getDefaultValue()).append(' ').append(setting.getValue())
						.toString();
			}
		};
	}

	public Converter<Integer, Setting> getIdToSettingConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Setting>() {
			public ro.roda.domain.Setting convert(java.lang.Integer id) {
				return settingService.findSetting(id);
			}
		};
	}

	public Converter<String, Setting> getStringToSettingConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Setting>() {
			public ro.roda.domain.Setting convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Setting.class);
			}
		};
	}

	public Converter<Skip, String> getSkipToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Skip, java.lang.String>() {
			public String convert(Skip skip) {
				return new StringBuilder().append(skip.getCondition()).toString();
			}
		};
	}

	public Converter<Long, Skip> getIdToSkipConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Skip>() {
			public ro.roda.domain.Skip convert(java.lang.Long id) {
				return skipService.findSkip(id);
			}
		};
	}

	public Converter<String, Skip> getStringToSkipConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Skip>() {
			public ro.roda.domain.Skip convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Skip.class);
			}
		};
	}

	public Converter<Source, String> getSourceToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Source, java.lang.String>() {
			public String convert(Source source) {
				return new StringBuilder().append(source.getCitation()).toString();
			}
		};
	}

	public Converter<Integer, Source> getIdToSourceConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Source>() {
			public ro.roda.domain.Source convert(java.lang.Integer id) {
				return sourceService.findSource(id);
			}
		};
	}

	public Converter<String, Source> getStringToSourceConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Source>() {
			public ro.roda.domain.Source convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Source.class);
			}
		};
	}

	public Converter<Study, String> getStudyToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Study, java.lang.String>() {
			public String convert(Study study) {
				return new StringBuilder().append(study.getDateStart()).append(' ').append(study.getDateEnd())
						.append(' ').append(study.getInsertionStatus()).append(' ').append(study.getAdded()).toString();
			}
		};
	}

	public Converter<Integer, Study> getIdToStudyConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Study>() {
			public ro.roda.domain.Study convert(java.lang.Integer id) {
				return studyService.findStudy(id);
			}
		};
	}

	public Converter<String, Study> getStringToStudyConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Study>() {
			public ro.roda.domain.Study convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Study.class);
			}
		};
	}

	public Converter<StudyDescr, String> getStudyDescrToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyDescr, java.lang.String>() {
			public String convert(StudyDescr studyDescr) {
				return new StringBuilder().append(studyDescr.getAbstract1()).append(' ')
						.append(studyDescr.getGrantDetails()).append(' ').append(studyDescr.getTitle()).append(' ')
						.append(studyDescr.getNotes()).toString();
			}
		};
	}

	public Converter<StudyDescrPK, StudyDescr> getIdToStudyDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyDescrPK, ro.roda.domain.StudyDescr>() {
			public ro.roda.domain.StudyDescr convert(ro.roda.domain.StudyDescrPK id) {
				return studyDescrService.findStudyDescr(id);
			}
		};
	}

	public Converter<String, StudyDescr> getStringToStudyDescrConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyDescr>() {
			public ro.roda.domain.StudyDescr convert(String id) {
				return getObject().convert(getObject().convert(id, StudyDescrPK.class), StudyDescr.class);
			}
		};
	}

	public Converter<StudyKeyword, String> getStudyKeywordToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyKeyword, java.lang.String>() {
			public String convert(StudyKeyword studyKeyword) {
				return new StringBuilder().append(studyKeyword.getAdded()).toString();
			}
		};
	}

	public Converter<StudyKeywordPK, StudyKeyword> getIdToStudyKeywordConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyKeywordPK, ro.roda.domain.StudyKeyword>() {
			public ro.roda.domain.StudyKeyword convert(ro.roda.domain.StudyKeywordPK id) {
				return studyKeywordService.findStudyKeyword(id);
			}
		};
	}

	public Converter<String, StudyKeyword> getStringToStudyKeywordConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyKeyword>() {
			public ro.roda.domain.StudyKeyword convert(String id) {
				return getObject().convert(getObject().convert(id, StudyKeywordPK.class), StudyKeyword.class);
			}
		};
	}

	public Converter<StudyOrg, String> getStudyOrgToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyOrg, java.lang.String>() {
			public String convert(StudyOrg studyOrg) {
				return new StringBuilder().append(studyOrg.getAssocDetails()).toString();
			}
		};
	}

	public Converter<StudyOrgPK, StudyOrg> getIdToStudyOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyOrgPK, ro.roda.domain.StudyOrg>() {
			public ro.roda.domain.StudyOrg convert(ro.roda.domain.StudyOrgPK id) {
				return studyOrgService.findStudyOrg(id);
			}
		};
	}

	public Converter<String, StudyOrg> getStringToStudyOrgConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyOrg>() {
			public ro.roda.domain.StudyOrg convert(String id) {
				return getObject().convert(getObject().convert(id, StudyOrgPK.class), StudyOrg.class);
			}
		};
	}

	public Converter<StudyOrgAssoc, String> getStudyOrgAssocToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyOrgAssoc, java.lang.String>() {
			public String convert(StudyOrgAssoc studyOrgAssoc) {
				return new StringBuilder().append(studyOrgAssoc.getAssocName()).append(' ')
						.append(studyOrgAssoc.getAssocDescription()).toString();
			}
		};
	}

	public Converter<Integer, StudyOrgAssoc> getIdToStudyOrgAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.StudyOrgAssoc>() {
			public ro.roda.domain.StudyOrgAssoc convert(java.lang.Integer id) {
				return studyOrgAssocService.findStudyOrgAssoc(id);
			}
		};
	}

	public Converter<String, StudyOrgAssoc> getStringToStudyOrgAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyOrgAssoc>() {
			public ro.roda.domain.StudyOrgAssoc convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), StudyOrgAssoc.class);
			}
		};
	}

	public Converter<StudyPerson, String> getStudyPersonToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyPerson, java.lang.String>() {
			public String convert(StudyPerson studyPerson) {
				return new StringBuilder().append(studyPerson.getAssocDetails()).toString();
			}
		};
	}

	public Converter<StudyPersonPK, StudyPerson> getIdToStudyPersonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyPersonPK, ro.roda.domain.StudyPerson>() {
			public ro.roda.domain.StudyPerson convert(ro.roda.domain.StudyPersonPK id) {
				return studyPersonService.findStudyPerson(id);
			}
		};
	}

	public Converter<String, StudyPerson> getStringToStudyPersonConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyPerson>() {
			public ro.roda.domain.StudyPerson convert(String id) {
				return getObject().convert(getObject().convert(id, StudyPersonPK.class), StudyPerson.class);
			}
		};
	}

	public Converter<StudyPersonAssoc, String> getStudyPersonAssocToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyPersonAssoc, java.lang.String>() {
			public String convert(StudyPersonAssoc studyPersonAssoc) {
				return new StringBuilder().append(studyPersonAssoc.getAsocName()).append(' ')
						.append(studyPersonAssoc.getAsocDescription()).toString();
			}
		};
	}

	public Converter<Integer, StudyPersonAssoc> getIdToStudyPersonAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.StudyPersonAssoc>() {
			public ro.roda.domain.StudyPersonAssoc convert(java.lang.Integer id) {
				return studyPersonAssocService.findStudyPersonAssoc(id);
			}
		};
	}

	public Converter<String, StudyPersonAssoc> getStringToStudyPersonAssocConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyPersonAssoc>() {
			public ro.roda.domain.StudyPersonAssoc convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), StudyPersonAssoc.class);
			}
		};
	}

	public Converter<Suffix, String> getSuffixToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Suffix, java.lang.String>() {
			public String convert(Suffix suffix) {
				return new StringBuilder().append(suffix.getName()).toString();
			}
		};
	}

	public Converter<Integer, Suffix> getIdToSuffixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Suffix>() {
			public ro.roda.domain.Suffix convert(java.lang.Integer id) {
				return suffixService.findSuffix(id);
			}
		};
	}

	public Converter<String, Suffix> getStringToSuffixConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Suffix>() {
			public ro.roda.domain.Suffix convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Suffix.class);
			}
		};
	}

	public Converter<TargetGroup, String> getTargetGroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.TargetGroup, java.lang.String>() {
			public String convert(TargetGroup targetGroup) {
				return new StringBuilder().append(targetGroup.getName()).toString();
			}
		};
	}

	public Converter<Integer, TargetGroup> getIdToTargetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.TargetGroup>() {
			public ro.roda.domain.TargetGroup convert(java.lang.Integer id) {
				return targetGroupService.findTargetGroup(id);
			}
		};
	}

	public Converter<String, TargetGroup> getStringToTargetGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.TargetGroup>() {
			public ro.roda.domain.TargetGroup convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), TargetGroup.class);
			}
		};
	}

	public Converter<TimeMeth, String> getTimeMethTypeToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.TimeMeth, java.lang.String>() {
			public String convert(TimeMeth timeMeth) {
				return new StringBuilder().append(timeMeth.getName()).append(' ').append(timeMeth.getDescription())
						.toString();
			}
		};
	}

	public Converter<Integer, TimeMeth> getIdToTimeMethTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.TimeMeth>() {
			public ro.roda.domain.TimeMeth convert(java.lang.Integer id) {
				return timeMethTypeService.findTimeMeth(id);
			}
		};
	}

	public Converter<String, TimeMeth> getStringToTimeMethTypeConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.TimeMeth>() {
			public ro.roda.domain.TimeMeth convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), TimeMeth.class);
			}
		};
	}

	public Converter<Topic, String> getTopicToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Topic, java.lang.String>() {
			public String convert(Topic topic) {
				return new StringBuilder().append(topic.getName()).append(' ').append(topic.getDescription())
						.toString();
			}
		};
	}

	public Converter<Integer, Topic> getIdToTopicConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Topic>() {
			public ro.roda.domain.Topic convert(java.lang.Integer id) {
				return topicService.findTopic(id);
			}
		};
	}

	public Converter<String, Topic> getStringToTopicConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Topic>() {
			public ro.roda.domain.Topic convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Topic.class);
			}
		};
	}

	public Converter<TranslatedTopic, String> getTranslatedTopicToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.TranslatedTopic, java.lang.String>() {
			public String convert(TranslatedTopic translatedTopic) {
				return new StringBuilder().append(translatedTopic.getTranslation()).toString();
			}
		};
	}

	public Converter<TranslatedTopicPK, TranslatedTopic> getIdToTranslatedTopicConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.TranslatedTopicPK, ro.roda.domain.TranslatedTopic>() {
			public ro.roda.domain.TranslatedTopic convert(ro.roda.domain.TranslatedTopicPK id) {
				return translatedTopicService.findTranslatedTopic(id);
			}
		};
	}

	public Converter<String, TranslatedTopic> getStringToTranslatedTopicConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.TranslatedTopic>() {
			public ro.roda.domain.TranslatedTopic convert(String id) {
				return getObject().convert(getObject().convert(id, TranslatedTopicPK.class), TranslatedTopic.class);
			}
		};
	}

	public Converter<UnitAnalysis, String> getUnitAnalysisToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UnitAnalysis, java.lang.String>() {
			public String convert(UnitAnalysis unitAnalysis) {
				return new StringBuilder().append(unitAnalysis.getName()).append(' ')
						.append(unitAnalysis.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, UnitAnalysis> getIdToUnitAnalysisConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.UnitAnalysis>() {
			public ro.roda.domain.UnitAnalysis convert(java.lang.Integer id) {
				return unitAnalysisService.findUnitAnalysis(id);
			}
		};
	}

	public Converter<String, UnitAnalysis> getStringToUnitAnalysisConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UnitAnalysis>() {
			public ro.roda.domain.UnitAnalysis convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), UnitAnalysis.class);
			}
		};
	}

	public Converter<UserAuthLog, String> getUserAuthLogToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserAuthLog, java.lang.String>() {
			public String convert(UserAuthLog userAuthLog) {
				return new StringBuilder().append(userAuthLog.getAuthAttemptedAt()).append(' ')
						.append(userAuthLog.getAction()).append(' ').append(userAuthLog.getCredentialProvider())
						.append(' ').append(userAuthLog.getCredentialIdentifier()).toString();
			}
		};
	}

	public Converter<Long, UserAuthLog> getIdToUserAuthLogConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.UserAuthLog>() {
			public ro.roda.domain.UserAuthLog convert(java.lang.Long id) {
				return userAuthLogService.findUserAuthLog(id);
			}
		};
	}

	public Converter<String, UserAuthLog> getStringToUserAuthLogConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserAuthLog>() {
			public ro.roda.domain.UserAuthLog convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), UserAuthLog.class);
			}
		};
	}

	public Converter<UserMessage, String> getUserMessageToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserMessage, java.lang.String>() {
			public String convert(UserMessage userMessage) {
				return new StringBuilder().append(userMessage.getMessage()).toString();
			}
		};
	}

	public Converter<Integer, UserMessage> getIdToUserMessageConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.UserMessage>() {
			public ro.roda.domain.UserMessage convert(java.lang.Integer id) {
				return userMessageService.findUserMessage(id);
			}
		};
	}

	public Converter<String, UserMessage> getStringToUserMessageConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserMessage>() {
			public ro.roda.domain.UserMessage convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), UserMessage.class);
			}
		};
	}

	public Converter<UserSetting, String> getUserSettingToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserSetting, java.lang.String>() {
			public String convert(UserSetting userSetting) {
				return new StringBuilder().append(userSetting.getName()).append(' ')
						.append(userSetting.getDescription()).append(' ').append(userSetting.getDefaultValue())
						.toString();
			}
		};
	}

	public Converter<Integer, UserSetting> getIdToUserSettingConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.UserSetting>() {
			public ro.roda.domain.UserSetting convert(java.lang.Integer id) {
				return userSettingService.findUserSetting(id);
			}
		};
	}

	public Converter<String, UserSetting> getStringToUserSettingConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserSetting>() {
			public ro.roda.domain.UserSetting convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), UserSetting.class);
			}
		};
	}

	public Converter<UserSettingGroup, String> getUserSettingGroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserSettingGroup, java.lang.String>() {
			public String convert(UserSettingGroup userSettingGroup) {
				return new StringBuilder().append(userSettingGroup.getName()).append(' ')
						.append(userSettingGroup.getDescription()).toString();
			}
		};
	}

	public Converter<Integer, UserSettingGroup> getIdToUserSettingGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.UserSettingGroup>() {
			public ro.roda.domain.UserSettingGroup convert(java.lang.Integer id) {
				return userSettingGroupService.findUserSettingGroup(id);
			}
		};
	}

	public Converter<String, UserSettingGroup> getStringToUserSettingGroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserSettingGroup>() {
			public ro.roda.domain.UserSettingGroup convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), UserSettingGroup.class);
			}
		};
	}

	public Converter<UserSettingValue, String> getUserSettingValueToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserSettingValue, java.lang.String>() {
			public String convert(UserSettingValue userSettingValue) {
				return new StringBuilder().append(userSettingValue.getValue()).toString();
			}
		};
	}

	public Converter<UserSettingValuePK, UserSettingValue> getIdToUserSettingValueConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserSettingValuePK, ro.roda.domain.UserSettingValue>() {
			public ro.roda.domain.UserSettingValue convert(ro.roda.domain.UserSettingValuePK id) {
				return userSettingValueService.findUserSettingValue(id);
			}
		};
	}

	public Converter<String, UserSettingValue> getStringToUserSettingValueConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserSettingValue>() {
			public ro.roda.domain.UserSettingValue convert(String id) {
				return getObject().convert(getObject().convert(id, UserSettingValuePK.class), UserSettingValue.class);
			}
		};
	}

	public Converter<Integer, Users> getIdToUsersConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Integer, ro.roda.domain.Users>() {
			public ro.roda.domain.Users convert(java.lang.Integer id) {
				return usersService.findUsers(id);
			}
		};
	}

	public Converter<String, Users> getStringToUsersConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Users>() {
			public ro.roda.domain.Users convert(String id) {
				return getObject().convert(getObject().convert(id, Integer.class), Users.class);
			}
		};
	}

	public Converter<Value, String> getValueToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Value, java.lang.String>() {
			public String convert(Value value) {
				return new StringBuilder().append(value.getValue()).toString();
			}
		};
	}

	public Converter<Long, Value> getIdToValueConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Value>() {
			public ro.roda.domain.Value convert(java.lang.Long id) {
				return valueService.findValue(id);
			}
		};
	}

	public Converter<String, Value> getStringToValueConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Value>() {
			public ro.roda.domain.Value convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Value.class);
			}
		};
	}

	public Converter<Vargroup, String> getVargroupToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Vargroup, java.lang.String>() {
			public String convert(Vargroup vargroup) {
				return new StringBuilder().append(vargroup.getName()).toString();
			}
		};
	}

	public Converter<Long, Vargroup> getIdToVargroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Vargroup>() {
			public ro.roda.domain.Vargroup convert(java.lang.Long id) {
				return vargroupService.findVargroup(id);
			}
		};
	}

	public Converter<String, Vargroup> getStringToVargroupConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Vargroup>() {
			public ro.roda.domain.Vargroup convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Vargroup.class);
			}
		};
	}

	public Converter<Variable, String> getVariableToStringConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.Variable, java.lang.String>() {
			public String convert(Variable variable) {
				return new StringBuilder().append(variable.getLabel()).append(' ').append(variable.getType())
						.append(' ').append(variable.getOperatorInstructions()).append(' ')
						.append(variable.getVariableType()).toString();
			}
		};
	}

	public Converter<Long, Variable> getIdToVariableConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.Long, ro.roda.domain.Variable>() {
			public ro.roda.domain.Variable convert(java.lang.Long id) {
				return variableService.findVariable(id);
			}
		};
	}

	public Converter<String, Variable> getStringToVariableConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.Variable>() {
			public ro.roda.domain.Variable convert(String id) {
				return getObject().convert(getObject().convert(id, Long.class), Variable.class);
			}
		};
	}

	public Converter<String, OrgInternetPK> getJsonToOrgInternetPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgInternetPK>() {
			public OrgInternetPK convert(String encodedJson) {
				return OrgInternetPK.fromJsonToOrgInternetPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<OrgInternetPK, String> getOrgInternetPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgInternetPK, java.lang.String>() {
			public String convert(OrgInternetPK orgInternetPK) {
				return Base64.encodeBase64URLSafeString(orgInternetPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, CatalogStudyPK> getJsonToCatalogStudyPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.CatalogStudyPK>() {
			public CatalogStudyPK convert(String encodedJson) {
				return CatalogStudyPK.fromJsonToCatalogStudyPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<CatalogStudyPK, String> getCatalogStudyPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.CatalogStudyPK, java.lang.String>() {
			public String convert(CatalogStudyPK catalogStudyPK) {
				return Base64.encodeBase64URLSafeString(catalogStudyPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, OrgAddressPK> getJsonToOrgAddressPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgAddressPK>() {
			public OrgAddressPK convert(String encodedJson) {
				return OrgAddressPK.fromJsonToOrgAddressPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<OrgAddressPK, String> getOrgAddressPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgAddressPK, java.lang.String>() {
			public String convert(OrgAddressPK orgAddressPK) {
				return Base64.encodeBase64URLSafeString(orgAddressPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, PersonAddressPK> getJsonToPersonAddressPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonAddressPK>() {
			public PersonAddressPK convert(String encodedJson) {
				return PersonAddressPK.fromJsonToPersonAddressPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<PersonAddressPK, String> getPersonAddressPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonAddressPK, java.lang.String>() {
			public String convert(PersonAddressPK personAddressPK) {
				return Base64.encodeBase64URLSafeString(personAddressPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, PersonEmailPK> getJsonToPersonEmailPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonEmailPK>() {
			public PersonEmailPK convert(String encodedJson) {
				return PersonEmailPK.fromJsonToPersonEmailPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<PersonEmailPK, String> getPersonEmailPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonEmailPK, java.lang.String>() {
			public String convert(PersonEmailPK personEmailPK) {
				return Base64.encodeBase64URLSafeString(personEmailPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, StudyDescrPK> getJsonToStudyDescrPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyDescrPK>() {
			public StudyDescrPK convert(String encodedJson) {
				return StudyDescrPK.fromJsonToStudyDescrPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<StudyDescrPK, String> getStudyDescrPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyDescrPK, java.lang.String>() {
			public String convert(StudyDescrPK studyDescrPK) {
				return Base64.encodeBase64URLSafeString(studyDescrPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, FormEditedTextVarPK> getJsonToFormEditedTextVarPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormEditedTextVarPK>() {
			public FormEditedTextVarPK convert(String encodedJson) {
				return FormEditedTextVarPK.fromJsonToFormEditedTextVarPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<FormEditedTextVarPK, String> getFormEditedTextVarPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedTextVarPK, java.lang.String>() {
			public String convert(FormEditedTextVarPK formEditedTextVarPK) {
				return Base64.encodeBase64URLSafeString(formEditedTextVarPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, InstanceFormPK> getJsonToInstanceFormPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceFormPK>() {
			public InstanceFormPK convert(String encodedJson) {
				return InstanceFormPK.fromJsonToInstanceFormPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<InstanceFormPK, String> getInstanceFormPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceFormPK, java.lang.String>() {
			public String convert(InstanceFormPK instanceFormPK) {
				return Base64.encodeBase64URLSafeString(instanceFormPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, AuthoritiesPK> getJsonToAuthoritiesPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.AuthoritiesPK>() {
			public AuthoritiesPK convert(String encodedJson) {
				return AuthoritiesPK.fromJsonToAuthoritiesPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<AuthoritiesPK, String> getAuthoritiesPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.AuthoritiesPK, java.lang.String>() {
			public String convert(AuthoritiesPK authoritiesPK) {
				return Base64.encodeBase64URLSafeString(authoritiesPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, PersonPhonePK> getJsonToPersonPhonePKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonPhonePK>() {
			public PersonPhonePK convert(String encodedJson) {
				return PersonPhonePK.fromJsonToPersonPhonePK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<PersonPhonePK, String> getPersonPhonePKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonPhonePK, java.lang.String>() {
			public String convert(PersonPhonePK personPhonePK) {
				return Base64.encodeBase64URLSafeString(personPhonePK.toJson().getBytes());
			}
		};
	}

	public Converter<String, StudyPersonPK> getJsonToStudyPersonPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyPersonPK>() {
			public StudyPersonPK convert(String encodedJson) {
				return StudyPersonPK.fromJsonToStudyPersonPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<StudyPersonPK, String> getStudyPersonPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyPersonPK, java.lang.String>() {
			public String convert(StudyPersonPK studyPersonPK) {
				return Base64.encodeBase64URLSafeString(studyPersonPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, InstancePersonPK> getJsonToInstancePersonPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstancePersonPK>() {
			public InstancePersonPK convert(String encodedJson) {
				return InstancePersonPK.fromJsonToInstancePersonPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<InstancePersonPK, String> getInstancePersonPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstancePersonPK, java.lang.String>() {
			public String convert(InstancePersonPK instancePersonPK) {
				return Base64.encodeBase64URLSafeString(instancePersonPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, OrgRelationsPK> getJsonToOrgRelationsPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgRelationsPK>() {
			public OrgRelationsPK convert(String encodedJson) {
				return OrgRelationsPK.fromJsonToOrgRelationsPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<OrgRelationsPK, String> getOrgRelationsPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgRelationsPK, java.lang.String>() {
			public String convert(OrgRelationsPK orgRelationsPK) {
				return Base64.encodeBase64URLSafeString(orgRelationsPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, PersonInternetPK> getJsonToPersonInternetPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonInternetPK>() {
			public PersonInternetPK convert(String encodedJson) {
				return PersonInternetPK.fromJsonToPersonInternetPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<PersonInternetPK, String> getPersonInternetPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonInternetPK, java.lang.String>() {
			public String convert(PersonInternetPK personInternetPK) {
				return Base64.encodeBase64URLSafeString(personInternetPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, InstanceDescrPK> getJsonToInstanceDescrPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceDescrPK>() {
			public InstanceDescrPK convert(String encodedJson) {
				return InstanceDescrPK.fromJsonToInstanceDescrPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<InstanceDescrPK, String> getInstanceDescrPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceDescrPK, java.lang.String>() {
			public String convert(InstanceDescrPK instanceDescrPK) {
				return Base64.encodeBase64URLSafeString(instanceDescrPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, FormSelectionVarPK> getJsonToFormSelectionVarPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormSelectionVarPK>() {
			public FormSelectionVarPK convert(String encodedJson) {
				return FormSelectionVarPK.fromJsonToFormSelectionVarPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<FormSelectionVarPK, String> getFormSelectionVarPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormSelectionVarPK, java.lang.String>() {
			public String convert(FormSelectionVarPK formSelectionVarPK) {
				return Base64.encodeBase64URLSafeString(formSelectionVarPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, SelectionVariableItemPK> getJsonToSelectionVariableItemPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SelectionVariableItemPK>() {
			public SelectionVariableItemPK convert(String encodedJson) {
				return SelectionVariableItemPK.fromJsonToSelectionVariableItemPK(new String(Base64
						.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<SelectionVariableItemPK, String> getSelectionVariableItemPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SelectionVariableItemPK, java.lang.String>() {
			public String convert(SelectionVariableItemPK selectionVariableItemPK) {
				return Base64.encodeBase64URLSafeString(selectionVariableItemPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, InstanceOrgPK> getJsonToInstanceOrgPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceOrgPK>() {
			public InstanceOrgPK convert(String encodedJson) {
				return InstanceOrgPK.fromJsonToInstanceOrgPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<InstanceOrgPK, String> getInstanceOrgPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceOrgPK, java.lang.String>() {
			public String convert(InstanceOrgPK instanceOrgPK) {
				return Base64.encodeBase64URLSafeString(instanceOrgPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, FormEditedNumberVarPK> getJsonToFormEditedNumberVarPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.FormEditedNumberVarPK>() {
			public FormEditedNumberVarPK convert(String encodedJson) {
				return FormEditedNumberVarPK.fromJsonToFormEditedNumberVarPK(new String(Base64
						.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<FormEditedNumberVarPK, String> getFormEditedNumberVarPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.FormEditedNumberVarPK, java.lang.String>() {
			public String convert(FormEditedNumberVarPK formEditedNumberVarPK) {
				return Base64.encodeBase64URLSafeString(formEditedNumberVarPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, OrgPhonePK> getJsonToOrgPhonePKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgPhonePK>() {
			public OrgPhonePK convert(String encodedJson) {
				return OrgPhonePK.fromJsonToOrgPhonePK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<OrgPhonePK, String> getOrgPhonePKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgPhonePK, java.lang.String>() {
			public String convert(OrgPhonePK orgPhonePK) {
				return Base64.encodeBase64URLSafeString(orgPhonePK.toJson().getBytes());
			}
		};
	}

	public Converter<String, PersonOrgPK> getJsonToPersonOrgPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.PersonOrgPK>() {
			public PersonOrgPK convert(String encodedJson) {
				return PersonOrgPK.fromJsonToPersonOrgPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<PersonOrgPK, String> getPersonOrgPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.PersonOrgPK, java.lang.String>() {
			public String convert(PersonOrgPK personOrgPK) {
				return Base64.encodeBase64URLSafeString(personOrgPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, StudyOrgPK> getJsonToStudyOrgPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyOrgPK>() {
			public StudyOrgPK convert(String encodedJson) {
				return StudyOrgPK.fromJsonToStudyOrgPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<StudyOrgPK, String> getStudyOrgPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyOrgPK, java.lang.String>() {
			public String convert(StudyOrgPK studyOrgPK) {
				return Base64.encodeBase64URLSafeString(studyOrgPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, UserSettingValuePK> getJsonToUserSettingValuePKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.UserSettingValuePK>() {
			public UserSettingValuePK convert(String encodedJson) {
				return UserSettingValuePK.fromJsonToUserSettingValuePK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<UserSettingValuePK, String> getUserSettingValuePKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.UserSettingValuePK, java.lang.String>() {
			public String convert(UserSettingValuePK userSettingValuePK) {
				return Base64.encodeBase64URLSafeString(userSettingValuePK.toJson().getBytes());
			}
		};
	}

	public Converter<String, TranslatedTopicPK> getJsonToTranslatedTopicPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.TranslatedTopicPK>() {
			public TranslatedTopicPK convert(String encodedJson) {
				return TranslatedTopicPK.fromJsonToTranslatedTopicPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<TranslatedTopicPK, String> getTranslatedTopicPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.TranslatedTopicPK, java.lang.String>() {
			public String convert(TranslatedTopicPK translatedTopicPK) {
				return Base64.encodeBase64URLSafeString(translatedTopicPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, OrgEmailPK> getJsonToOrgEmailPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.OrgEmailPK>() {
			public OrgEmailPK convert(String encodedJson) {
				return OrgEmailPK.fromJsonToOrgEmailPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<OrgEmailPK, String> getOrgEmailPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.OrgEmailPK, java.lang.String>() {
			public String convert(OrgEmailPK orgEmailPK) {
				return Base64.encodeBase64URLSafeString(orgEmailPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, SeriesDescrPK> getJsonToSeriesDescrPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.SeriesDescrPK>() {
			public SeriesDescrPK convert(String encodedJson) {
				return SeriesDescrPK.fromJsonToSeriesDescrPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<SeriesDescrPK, String> getSeriesDescrPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.SeriesDescrPK, java.lang.String>() {
			public String convert(SeriesDescrPK seriesDescrPK) {
				return Base64.encodeBase64URLSafeString(seriesDescrPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, StudyKeywordPK> getJsonToStudyKeywordPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.StudyKeywordPK>() {
			public StudyKeywordPK convert(String encodedJson) {
				return StudyKeywordPK.fromJsonToStudyKeywordPK(new String(Base64.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<StudyKeywordPK, String> getStudyKeywordPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.StudyKeywordPK, java.lang.String>() {
			public String convert(StudyKeywordPK studyKeywordPK) {
				return Base64.encodeBase64URLSafeString(studyKeywordPK.toJson().getBytes());
			}
		};
	}

	public Converter<String, InstanceRightTargetGroupPK> getJsonToInstanceRightTargetGroupPKConverter() {
		return new org.springframework.core.convert.converter.Converter<java.lang.String, ro.roda.domain.InstanceRightTargetGroupPK>() {
			public InstanceRightTargetGroupPK convert(String encodedJson) {
				return InstanceRightTargetGroupPK.fromJsonToInstanceRightTargetGroupPK(new String(Base64
						.decodeBase64(encodedJson)));
			}
		};
	}

	public Converter<InstanceRightTargetGroupPK, String> getInstanceRightTargetGroupPKToJsonConverter() {
		return new org.springframework.core.convert.converter.Converter<ro.roda.domain.InstanceRightTargetGroupPK, java.lang.String>() {
			public String convert(InstanceRightTargetGroupPK instanceRightTargetGroupPK) {
				return Base64.encodeBase64URLSafeString(instanceRightTargetGroupPK.toJson().getBytes());
			}
		};
	}

	public void installLabelConverters(FormatterRegistry registry) {
		registry.addConverter(getAclClassToStringConverter());
		registry.addConverter(getIdToAclClassConverter());
		registry.addConverter(getStringToAclClassConverter());
		registry.addConverter(getAclEntryToStringConverter());
		registry.addConverter(getIdToAclEntryConverter());
		registry.addConverter(getStringToAclEntryConverter());
		registry.addConverter(getAclObjectIdentityToStringConverter());
		registry.addConverter(getIdToAclObjectIdentityConverter());
		registry.addConverter(getStringToAclObjectIdentityConverter());
		registry.addConverter(getAclSidToStringConverter());
		registry.addConverter(getIdToAclSidConverter());
		registry.addConverter(getStringToAclSidConverter());
		registry.addConverter(getAddressToStringConverter());
		registry.addConverter(getIdToAddressConverter());
		registry.addConverter(getStringToAddressConverter());
		registry.addConverter(getAuthoritiesToStringConverter());
		registry.addConverter(getIdToAuthoritiesConverter());
		registry.addConverter(getStringToAuthoritiesConverter());
		registry.addConverter(getCatalogToStringConverter());
		registry.addConverter(getIdToCatalogConverter());
		registry.addConverter(getStringToCatalogConverter());
		registry.addConverter(getCatalogStudyToStringConverter());
		registry.addConverter(getIdToCatalogStudyConverter());
		registry.addConverter(getStringToCatalogStudyConverter());
		registry.addConverter(getCityToStringConverter());
		registry.addConverter(getIdToCityConverter());
		registry.addConverter(getStringToCityConverter());
		registry.addConverter(getCmsFileToStringConverter());
		registry.addConverter(getIdToCmsFileConverter());
		registry.addConverter(getStringToCmsFileConverter());
		registry.addConverter(getCmsFolderToStringConverter());
		registry.addConverter(getIdToCmsFolderConverter());
		registry.addConverter(getStringToCmsFolderConverter());
		registry.addConverter(getCmsLayoutToStringConverter());
		registry.addConverter(getIdToCmsLayoutConverter());
		registry.addConverter(getStringToCmsLayoutConverter());
		registry.addConverter(getCmsLayoutGroupToStringConverter());
		registry.addConverter(getIdToCmsLayoutGroupConverter());
		registry.addConverter(getStringToCmsLayoutGroupConverter());
		registry.addConverter(getCmsPageToStringConverter());
		registry.addConverter(getIdToCmsPageConverter());
		registry.addConverter(getStringToCmsPageConverter());
		registry.addConverter(getCmsPageContentToStringConverter());
		registry.addConverter(getIdToCmsPageContentConverter());
		registry.addConverter(getStringToCmsPageContentConverter());
		registry.addConverter(getCmsPageTypeToStringConverter());
		registry.addConverter(getIdToCmsPageTypeConverter());
		registry.addConverter(getStringToCmsPageTypeConverter());
		registry.addConverter(getCmsSnippetToStringConverter());
		registry.addConverter(getIdToCmsSnippetConverter());
		registry.addConverter(getStringToCmsSnippetConverter());
		registry.addConverter(getCmsSnippetGroupToStringConverter());
		registry.addConverter(getIdToCmsSnippetGroupConverter());
		registry.addConverter(getStringToCmsSnippetGroupConverter());
		registry.addConverter(getCollectionModelTypeToStringConverter());
		registry.addConverter(getIdToCollectionModelTypeConverter());
		registry.addConverter(getStringToCollectionModelTypeConverter());
		registry.addConverter(getConceptToStringConverter());
		registry.addConverter(getIdToConceptConverter());
		registry.addConverter(getStringToConceptConverter());
		registry.addConverter(getCountryToStringConverter());
		registry.addConverter(getIdToCountryConverter());
		registry.addConverter(getStringToCountryConverter());
		registry.addConverter(getDataSourceTypeToStringConverter());
		registry.addConverter(getIdToDataSourceTypeConverter());
		registry.addConverter(getStringToDataSourceTypeConverter());
		registry.addConverter(getEmailToStringConverter());
		registry.addConverter(getIdToEmailConverter());
		registry.addConverter(getStringToEmailConverter());
		registry.addConverter(getFileToStringConverter());
		registry.addConverter(getIdToFileConverter());
		registry.addConverter(getStringToFileConverter());
		registry.addConverter(getFormToStringConverter());
		registry.addConverter(getIdToFormConverter());
		registry.addConverter(getStringToFormConverter());
		registry.addConverter(getFormEditedNumberVarToStringConverter());
		registry.addConverter(getIdToFormEditedNumberVarConverter());
		registry.addConverter(getStringToFormEditedNumberVarConverter());
		registry.addConverter(getFormEditedTextVarToStringConverter());
		registry.addConverter(getIdToFormEditedTextVarConverter());
		registry.addConverter(getStringToFormEditedTextVarConverter());
		registry.addConverter(getFormSelectionVarToStringConverter());
		registry.addConverter(getIdToFormSelectionVarConverter());
		registry.addConverter(getStringToFormSelectionVarConverter());
		registry.addConverter(getInstanceToStringConverter());
		registry.addConverter(getIdToInstanceConverter());
		registry.addConverter(getStringToInstanceConverter());
		registry.addConverter(getInstanceDescrToStringConverter());
		registry.addConverter(getIdToInstanceDescrConverter());
		registry.addConverter(getStringToInstanceDescrConverter());
		registry.addConverter(getInstanceFormToStringConverter());
		registry.addConverter(getIdToInstanceFormConverter());
		registry.addConverter(getStringToInstanceFormConverter());
		registry.addConverter(getInstanceOrgToStringConverter());
		registry.addConverter(getIdToInstanceOrgConverter());
		registry.addConverter(getStringToInstanceOrgConverter());
		registry.addConverter(getInstanceOrgAssocToStringConverter());
		registry.addConverter(getIdToInstanceOrgAssocConverter());
		registry.addConverter(getStringToInstanceOrgAssocConverter());
		registry.addConverter(getInstancePersonToStringConverter());
		registry.addConverter(getIdToInstancePersonConverter());
		registry.addConverter(getStringToInstancePersonConverter());
		registry.addConverter(getInstancePersonAssocToStringConverter());
		registry.addConverter(getIdToInstancePersonAssocConverter());
		registry.addConverter(getStringToInstancePersonAssocConverter());
		registry.addConverter(getInstanceRightToStringConverter());
		registry.addConverter(getIdToInstanceRightConverter());
		registry.addConverter(getStringToInstanceRightConverter());
		registry.addConverter(getInstanceRightTargetGroupToStringConverter());
		registry.addConverter(getIdToInstanceRightTargetGroupConverter());
		registry.addConverter(getStringToInstanceRightTargetGroupConverter());
		registry.addConverter(getInstanceRightValueToStringConverter());
		registry.addConverter(getIdToInstanceRightValueConverter());
		registry.addConverter(getStringToInstanceRightValueConverter());
		registry.addConverter(getInternetToStringConverter());
		registry.addConverter(getIdToInternetConverter());
		registry.addConverter(getStringToInternetConverter());
		registry.addConverter(getItemToStringConverter());
		registry.addConverter(getIdToItemConverter());
		registry.addConverter(getStringToItemConverter());
		registry.addConverter(getKeywordToStringConverter());
		registry.addConverter(getIdToKeywordConverter());
		registry.addConverter(getStringToKeywordConverter());
		registry.addConverter(getLangToStringConverter());
		registry.addConverter(getIdToLangConverter());
		registry.addConverter(getStringToLangConverter());
		registry.addConverter(getNewsToStringConverter());
		registry.addConverter(getIdToNewsConverter());
		registry.addConverter(getStringToNewsConverter());
		registry.addConverter(getOrgToStringConverter());
		registry.addConverter(getIdToOrgConverter());
		registry.addConverter(getStringToOrgConverter());
		registry.addConverter(getOrgAddressToStringConverter());
		registry.addConverter(getIdToOrgAddressConverter());
		registry.addConverter(getStringToOrgAddressConverter());
		registry.addConverter(getOrgEmailToStringConverter());
		registry.addConverter(getIdToOrgEmailConverter());
		registry.addConverter(getStringToOrgEmailConverter());
		registry.addConverter(getOrgInternetToStringConverter());
		registry.addConverter(getIdToOrgInternetConverter());
		registry.addConverter(getStringToOrgInternetConverter());
		registry.addConverter(getOrgPhoneToStringConverter());
		registry.addConverter(getIdToOrgPhoneConverter());
		registry.addConverter(getStringToOrgPhoneConverter());
		registry.addConverter(getOrgPrefixToStringConverter());
		registry.addConverter(getIdToOrgPrefixConverter());
		registry.addConverter(getStringToOrgPrefixConverter());
		registry.addConverter(getOrgRelationTypeToStringConverter());
		registry.addConverter(getIdToOrgRelationTypeConverter());
		registry.addConverter(getStringToOrgRelationTypeConverter());
		registry.addConverter(getOrgRelationsToStringConverter());
		registry.addConverter(getIdToOrgRelationsConverter());
		registry.addConverter(getStringToOrgRelationsConverter());
		registry.addConverter(getOrgSufixToStringConverter());
		registry.addConverter(getIdToOrgSufixConverter());
		registry.addConverter(getStringToOrgSufixConverter());
		registry.addConverter(getOtherStatisticToStringConverter());
		registry.addConverter(getIdToOtherStatisticConverter());
		registry.addConverter(getStringToOtherStatisticConverter());
		registry.addConverter(getPersonToStringConverter());
		registry.addConverter(getIdToPersonConverter());
		registry.addConverter(getStringToPersonConverter());
		registry.addConverter(getPersonAddressToStringConverter());
		registry.addConverter(getIdToPersonAddressConverter());
		registry.addConverter(getStringToPersonAddressConverter());
		registry.addConverter(getPersonEmailToStringConverter());
		registry.addConverter(getIdToPersonEmailConverter());
		registry.addConverter(getStringToPersonEmailConverter());
		registry.addConverter(getPersonInternetToStringConverter());
		registry.addConverter(getIdToPersonInternetConverter());
		registry.addConverter(getStringToPersonInternetConverter());
		registry.addConverter(getPersonLinksToStringConverter());
		registry.addConverter(getIdToPersonLinksConverter());
		registry.addConverter(getStringToPersonLinksConverter());
		registry.addConverter(getPersonOrgToStringConverter());
		registry.addConverter(getIdToPersonOrgConverter());
		registry.addConverter(getStringToPersonOrgConverter());
		registry.addConverter(getPersonPhoneToStringConverter());
		registry.addConverter(getIdToPersonPhoneConverter());
		registry.addConverter(getStringToPersonPhoneConverter());
		registry.addConverter(getPersonRoleToStringConverter());
		registry.addConverter(getIdToPersonRoleConverter());
		registry.addConverter(getStringToPersonRoleConverter());
		registry.addConverter(getPhoneToStringConverter());
		registry.addConverter(getIdToPhoneConverter());
		registry.addConverter(getStringToPhoneConverter());
		registry.addConverter(getPrefixToStringConverter());
		registry.addConverter(getIdToPrefixConverter());
		registry.addConverter(getStringToPrefixConverter());
		registry.addConverter(getRegionToStringConverter());
		registry.addConverter(getIdToRegionConverter());
		registry.addConverter(getStringToRegionConverter());
		registry.addConverter(getRegiontypeToStringConverter());
		registry.addConverter(getIdToRegiontypeConverter());
		registry.addConverter(getStringToRegiontypeConverter());
		registry.addConverter(getSamplingProcedureToStringConverter());
		registry.addConverter(getIdToSamplingProcedureConverter());
		registry.addConverter(getStringToSamplingProcedureConverter());
		registry.addConverter(getScaleToStringConverter());
		registry.addConverter(getIdToScaleConverter());
		registry.addConverter(getStringToScaleConverter());
		registry.addConverter(getSelectionVariableToStringConverter());
		registry.addConverter(getIdToSelectionVariableConverter());
		registry.addConverter(getStringToSelectionVariableConverter());
		registry.addConverter(getSelectionVariableItemToStringConverter());
		registry.addConverter(getIdToSelectionVariableItemConverter());
		registry.addConverter(getStringToSelectionVariableItemConverter());
		registry.addConverter(getSeriesToStringConverter());
		registry.addConverter(getIdToSeriesConverter());
		registry.addConverter(getStringToSeriesConverter());
		registry.addConverter(getSeriesDescrToStringConverter());
		registry.addConverter(getIdToSeriesDescrConverter());
		registry.addConverter(getStringToSeriesDescrConverter());
		registry.addConverter(getSettingToStringConverter());
		registry.addConverter(getIdToSettingConverter());
		registry.addConverter(getStringToSettingConverter());
		registry.addConverter(getSkipToStringConverter());
		registry.addConverter(getIdToSkipConverter());
		registry.addConverter(getStringToSkipConverter());
		registry.addConverter(getSourceToStringConverter());
		registry.addConverter(getIdToSourceConverter());
		registry.addConverter(getStringToSourceConverter());
		registry.addConverter(getStudyToStringConverter());
		registry.addConverter(getIdToStudyConverter());
		registry.addConverter(getStringToStudyConverter());
		registry.addConverter(getStudyDescrToStringConverter());
		registry.addConverter(getIdToStudyDescrConverter());
		registry.addConverter(getStringToStudyDescrConverter());
		registry.addConverter(getStudyKeywordToStringConverter());
		registry.addConverter(getIdToStudyKeywordConverter());
		registry.addConverter(getStringToStudyKeywordConverter());
		registry.addConverter(getStudyOrgToStringConverter());
		registry.addConverter(getIdToStudyOrgConverter());
		registry.addConverter(getStringToStudyOrgConverter());
		registry.addConverter(getStudyOrgAssocToStringConverter());
		registry.addConverter(getIdToStudyOrgAssocConverter());
		registry.addConverter(getStringToStudyOrgAssocConverter());
		registry.addConverter(getStudyPersonToStringConverter());
		registry.addConverter(getIdToStudyPersonConverter());
		registry.addConverter(getStringToStudyPersonConverter());
		registry.addConverter(getStudyPersonAssocToStringConverter());
		registry.addConverter(getIdToStudyPersonAssocConverter());
		registry.addConverter(getStringToStudyPersonAssocConverter());
		registry.addConverter(getSuffixToStringConverter());
		registry.addConverter(getIdToSuffixConverter());
		registry.addConverter(getStringToSuffixConverter());
		registry.addConverter(getTargetGroupToStringConverter());
		registry.addConverter(getIdToTargetGroupConverter());
		registry.addConverter(getStringToTargetGroupConverter());
		registry.addConverter(getTimeMethTypeToStringConverter());
		registry.addConverter(getIdToTimeMethTypeConverter());
		registry.addConverter(getStringToTimeMethTypeConverter());
		registry.addConverter(getTopicToStringConverter());
		registry.addConverter(getIdToTopicConverter());
		registry.addConverter(getStringToTopicConverter());
		registry.addConverter(getTranslatedTopicToStringConverter());
		registry.addConverter(getIdToTranslatedTopicConverter());
		registry.addConverter(getStringToTranslatedTopicConverter());
		registry.addConverter(getUnitAnalysisToStringConverter());
		registry.addConverter(getIdToUnitAnalysisConverter());
		registry.addConverter(getStringToUnitAnalysisConverter());
		registry.addConverter(getUserAuthLogToStringConverter());
		registry.addConverter(getIdToUserAuthLogConverter());
		registry.addConverter(getStringToUserAuthLogConverter());
		registry.addConverter(getUserMessageToStringConverter());
		registry.addConverter(getIdToUserMessageConverter());
		registry.addConverter(getStringToUserMessageConverter());
		registry.addConverter(getUserSettingToStringConverter());
		registry.addConverter(getIdToUserSettingConverter());
		registry.addConverter(getStringToUserSettingConverter());
		registry.addConverter(getUserSettingGroupToStringConverter());
		registry.addConverter(getIdToUserSettingGroupConverter());
		registry.addConverter(getStringToUserSettingGroupConverter());
		registry.addConverter(getUserSettingValueToStringConverter());
		registry.addConverter(getIdToUserSettingValueConverter());
		registry.addConverter(getStringToUserSettingValueConverter());
		registry.addConverter(getUsersToStringConverter());
		registry.addConverter(getIdToUsersConverter());
		registry.addConverter(getStringToUsersConverter());
		registry.addConverter(getValueToStringConverter());
		registry.addConverter(getIdToValueConverter());
		registry.addConverter(getStringToValueConverter());
		registry.addConverter(getVargroupToStringConverter());
		registry.addConverter(getIdToVargroupConverter());
		registry.addConverter(getStringToVargroupConverter());
		registry.addConverter(getVariableToStringConverter());
		registry.addConverter(getIdToVariableConverter());
		registry.addConverter(getStringToVariableConverter());
		registry.addConverter(getJsonToOrgInternetPKConverter());
		registry.addConverter(getOrgInternetPKToJsonConverter());
		registry.addConverter(getJsonToCatalogStudyPKConverter());
		registry.addConverter(getCatalogStudyPKToJsonConverter());
		registry.addConverter(getJsonToOrgAddressPKConverter());
		registry.addConverter(getOrgAddressPKToJsonConverter());
		registry.addConverter(getJsonToPersonAddressPKConverter());
		registry.addConverter(getPersonAddressPKToJsonConverter());
		registry.addConverter(getJsonToPersonEmailPKConverter());
		registry.addConverter(getPersonEmailPKToJsonConverter());
		registry.addConverter(getJsonToStudyDescrPKConverter());
		registry.addConverter(getStudyDescrPKToJsonConverter());
		registry.addConverter(getJsonToFormEditedTextVarPKConverter());
		registry.addConverter(getFormEditedTextVarPKToJsonConverter());
		registry.addConverter(getJsonToInstanceFormPKConverter());
		registry.addConverter(getInstanceFormPKToJsonConverter());
		registry.addConverter(getJsonToAuthoritiesPKConverter());
		registry.addConverter(getAuthoritiesPKToJsonConverter());
		registry.addConverter(getJsonToPersonPhonePKConverter());
		registry.addConverter(getPersonPhonePKToJsonConverter());
		registry.addConverter(getJsonToStudyPersonPKConverter());
		registry.addConverter(getStudyPersonPKToJsonConverter());
		registry.addConverter(getJsonToInstancePersonPKConverter());
		registry.addConverter(getInstancePersonPKToJsonConverter());
		registry.addConverter(getJsonToOrgRelationsPKConverter());
		registry.addConverter(getOrgRelationsPKToJsonConverter());
		registry.addConverter(getJsonToPersonInternetPKConverter());
		registry.addConverter(getPersonInternetPKToJsonConverter());
		registry.addConverter(getJsonToInstanceDescrPKConverter());
		registry.addConverter(getInstanceDescrPKToJsonConverter());
		registry.addConverter(getJsonToFormSelectionVarPKConverter());
		registry.addConverter(getFormSelectionVarPKToJsonConverter());
		registry.addConverter(getJsonToSelectionVariableItemPKConverter());
		registry.addConverter(getSelectionVariableItemPKToJsonConverter());
		registry.addConverter(getJsonToInstanceOrgPKConverter());
		registry.addConverter(getInstanceOrgPKToJsonConverter());
		registry.addConverter(getJsonToFormEditedNumberVarPKConverter());
		registry.addConverter(getFormEditedNumberVarPKToJsonConverter());
		registry.addConverter(getJsonToOrgPhonePKConverter());
		registry.addConverter(getOrgPhonePKToJsonConverter());
		registry.addConverter(getJsonToPersonOrgPKConverter());
		registry.addConverter(getPersonOrgPKToJsonConverter());
		registry.addConverter(getJsonToStudyOrgPKConverter());
		registry.addConverter(getStudyOrgPKToJsonConverter());
		registry.addConverter(getJsonToUserSettingValuePKConverter());
		registry.addConverter(getUserSettingValuePKToJsonConverter());
		registry.addConverter(getJsonToTranslatedTopicPKConverter());
		registry.addConverter(getTranslatedTopicPKToJsonConverter());
		registry.addConverter(getJsonToOrgEmailPKConverter());
		registry.addConverter(getOrgEmailPKToJsonConverter());
		registry.addConverter(getJsonToSeriesDescrPKConverter());
		registry.addConverter(getSeriesDescrPKToJsonConverter());
		registry.addConverter(getJsonToStudyKeywordPKConverter());
		registry.addConverter(getStudyKeywordPKToJsonConverter());
		registry.addConverter(getJsonToInstanceRightTargetGroupPKConverter());
		registry.addConverter(getInstanceRightTargetGroupPKToJsonConverter());
	}

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		installLabelConverters(getObject());
	}
}
