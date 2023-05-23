package com.proyecto404.backoffice.core.infrastructure.persistance.jooq

import com.proyecto404.backoffice.base.data.jooq.JooqConfiguration
import com.proyecto404.backoffice.base.domain.Id
import com.proyecto404.backoffice.core.domain.ChatbotConfiguration
import com.proyecto404.backoffice.core.domain.ChatbotConfigurations
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.daos.ChatbotConfigurationsDao
import com.proyecto404.backoffice.infrastructure.jooq.generated.tables.pojos.ChatbotConfigurationsDto
import org.jooq.exception.DataAccessException

class JooqChatbotConfigurations(val jooq: JooqConfiguration): ChatbotConfigurations {
    private val dao = ChatbotConfigurationsDao(jooq)

    override fun add(configuration: ChatbotConfiguration) {
        dao.update(configuration.toDto())
    }

    override fun get(): ChatbotConfiguration {
        return dao.findAll().single().toChatbotConfiguration()
    }

    private fun ChatbotConfigurationsDto.toChatbotConfiguration() = ChatbotConfiguration(
        Id(id),
        apiKey,
        context,
        maxTokens,
        temperature.toDouble(),
        topP.toDouble(),
        frequencyPenalty,
        parameterpresencePenalty
    )

    private fun ChatbotConfiguration.toDto() = ChatbotConfigurationsDto().also {
        it.id = id.toInt()
        it.apiKey = apiKey
        it.context = context
        it.temperature = temperature.toBigDecimal()
        it.topP = topP.toBigDecimal()
        it.frequencyPenalty = frequencyPenalty
        it.parameterpresencePenalty = parameterPresencePenalty

    }
//    override fun add(amenity: Amenity) {
//        val identity = identityContext.getIdentity()
//        captureKnownErrors {
//            amenity.withAudit(Audit.create(createdBy = UserAccount.fromIdentity(identity)))
//            val snapshot = amenity.snapshot()
//            dao.insert(snapshot.toDto())
//            insertImages(snapshot)
//            insertSchedules(snapshot)
//        }
//    }
//
//    override fun update(amenity: Amenity) {
//        val identity = identityContext.getIdentity()
//        captureKnownErrors {
//            amenity.withAudit(amenity.audit.updatedBy(UserAccount.fromIdentity(identity)))
//            val snapshot = amenity.snapshot()
//            dao.update(snapshot.toDto())
//            updateImages(snapshot)
//            updateSchedules(snapshot)
//        }
//    }
//
//
//    private fun AmenitiesDto.toAmenity(
//        images: List<AmenityImagesDto>,
//        schedules: List<SchedulesDto>,
//        slots: List<ScheduleSlotsDto>,
//    ) = Amenity.fromSnapshot(
//        Snapshot(
//            Id(id),
//            name,
//            description,
//            scheduleSummary,
//            bookingRulesFrom(this),
//            Id(categoryId),
//            CommunityId(communityId),
//            thumbnailPath,
//            images.map { it.path },
//            usageRules,
//            amenityOrder,
//            isEnabled,
//            schedules.map { it.toScheduleVersion(slots) },
//            Audit.create(createdAt, UserAccount(createdByFullname, createdById), updatedAt, UserAccount(updatedByFullname, updatedById)),
//            timeZone
//        )
//    )
//
//    private fun Snapshot.toDto() = AmenitiesDto().also {
//        it.id = id.toString()
//        it.name = name
//        it.communityId = communityId.toString()
//        it.categoryId = categoryId.toString()
//        it.isEnabled = isEnabled
//        it.thumbnailPath = thumbnailPath
//        it.description = description
//        it.usageRules = usageRules
//        it.bookingMinHoursCancel = bookingRules.minHoursToCancel
//        it.bookingMinHoursBook = bookingRules.minHoursToBook
//        it.bookingMaxDaysBook = bookingRules.maxDaysToBookInAdvance
//        it.bookingDefaultersCanBook = bookingRules.defaultersCanBook
//        it.scheduleSummary = scheduleSummary
//        it.amenityOrder = order
//        it.createdByFullname = audit.createdBy.fullname
//        it.createdById = audit.createdBy.accountId
//        it.createdAt = audit.createdAt
//        it.updatedByFullname = audit.updatedBy.fullname
//        it.updatedById = audit.updatedBy.accountId
//        it.updatedAt = audit.updateAt
//        it.timeZone = timeZone
//    }

    private fun <T> captureKnownErrors(block: () -> T): T {
        try {
            return block()
        } catch (e: DataAccessException) {
//            if (e.message?.contains("amenities_name_uq") == true) throw AlreadyExistsError()
//            if (e.message?.contains("foreign key constraint fails") == true) throw CannotDeleteError()
            throw e
        }
    }
}
